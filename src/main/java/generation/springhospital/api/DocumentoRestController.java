package generation.springhospital.api;

import hospital.spring.model.Documento;
import hospital.spring.services.GoogleCloudStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/files")
public class DocumentoRestController {

    @Autowired
    private GoogleCloudStorageService storageService;

    // Subir archivo para un paciente
    @PostMapping("/upload/paciente/{pacienteId}")
    public String uploadFileForPaciente(@PathVariable Long pacienteId, @RequestParam("file") MultipartFile file) throws IOException {
        return storageService.uploadFileForPaciente(pacienteId, file);
    }

    // Subir archivo para una cita
    @PostMapping("/upload/cita/{citaId}")
    public String uploadFileForCita(@PathVariable Long citaId, @RequestParam("file") MultipartFile file) throws IOException {
        return storageService.uploadFileForCita(citaId, file);
    }

    // Listar archivos para un paciente
    @GetMapping("/list/paciente/{pacienteId}")
    public List<Documento> listFilesForPaciente(@PathVariable Long pacienteId) {
        return storageService.listFilesForPaciente(pacienteId);
    }

    // Listar archivos para una cita
    @GetMapping("/list/cita/{citaId}")
    public List<Documento> listFilesForCita(@PathVariable Long citaId) {
        return storageService.listFilesForCita(citaId);
    }

    /*
    // Descargar archivo
    @GetMapping("/download/{fileName}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable String fileName) {
        byte[] data = storageService.downloadFile(fileName);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(data);
    }*/

    @GetMapping("/download/{pacienteId}/{fileName}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Long pacienteId, @PathVariable String fileName) {
        String fullPath = "pacientes/" + pacienteId + "/documentos/" + fileName;
        byte[] data = storageService.downloadFile(fullPath);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(data);
    }

    // Eliminar archivo
    @DeleteMapping("/delete/{fileName}")
    public String deleteFile(@PathVariable String fileName) {
        return storageService.deleteFile(fileName);
    }
}

