package generation.springhospital.services;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.*;
import hospital.spring.model.Cita;
import hospital.spring.model.Documento;
import hospital.spring.model.Paciente;
import hospital.spring.repositories.CitaRepository;
import hospital.spring.repositories.DocumentoRepository;
import hospital.spring.repositories.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@Service
public class GoogleCloudStorageService {
    private final String SERVICE_ACCOUNT_JSON_PATH;
    private final Storage storage;


    @Autowired
    private DocumentoRepository documentoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private CitaRepository citaRepository;

    {
        try {
            storage = StorageOptions.newBuilder()
                    .setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream(SERVICE_ACCOUNT_JSON_PATH)))
                    .build().getService();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Método para subir un archivo asociado a un paciente
    public String uploadFileForPaciente(Long pacienteId, MultipartFile file) throws IOException {

        Paciente paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));

        String filePath = "pacientes/" + pacienteId + "/documentos/" + file.getOriginalFilename();

        BlobId blobId = BlobId.of(bucketName, filePath);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(file.getContentType()).build();
        storage.create(blobInfo, file.getBytes());

        // Guardar información del archivo en la base de datos
        Documento documento = new Documento();
        documento.setNombreArchivo(file.getOriginalFilename());
        documento.setUrl(filePath);
        documento.setPaciente(paciente);
        documentoRepository.save(documento);


        return String.format("File %s uploaded to bucket %s as %s", file.getOriginalFilename(), bucketName, blobId.getName());
    }

    // Método para subir un archivo asociado a una cita
    public String uploadFileForCita(Long citaId, MultipartFile file) throws IOException {
        Cita cita = citaRepository.findById(citaId)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));

        String filePath = "citas/" + citaId + "/documentos/" + file.getOriginalFilename();
        BlobId blobId = BlobId.of(bucketName, filePath);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(file.getContentType()).build();
        storage.create(blobInfo, file.getBytes());

        // Guardar información del archivo en la base de datos
        Documento documento = new Documento();
        documento.setNombreArchivo(file.getOriginalFilename());
        documento.setUrl(filePath);
        documento.setCita(cita);
        documentoRepository.save(documento);

        return String.format("File %s uploaded to bucket %s as %s", file.getOriginalFilename(), bucketName, blobId.getName());
    }

    // Método para descargar un archivo
    public byte[] downloadFile(String fileName) {
        Blob blob = storage.get(BlobId.of(bucketName, fileName));
        return blob.getContent();
    }

    // Método para listar archivos de un paciente
    public List<Documento> listFilesForPaciente(Long pacienteId) {
        return documentoRepository.findByPacienteId(pacienteId);
    }

    // Método para listar archivos de una cita
    public List<Documento> listFilesForCita(Long citaId) {
        return documentoRepository.findByCitaId(citaId);
    }

    // Método para eliminar un archivo
    public String deleteFile(String fileName) {
        BlobId blobId = BlobId.of(bucketName, fileName);
        boolean deleted = storage.delete(blobId);
        return deleted ? "File deleted successfully" : "File not found";
    }
}