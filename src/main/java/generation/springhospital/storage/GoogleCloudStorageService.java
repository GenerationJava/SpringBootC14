package generation.springhospital.storage;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.*;

import generation.springhospital.models.Cita;
import generation.springhospital.models.Documento;
import generation.springhospital.models.Paciente;
import generation.springhospital.repositories.CitaRepository;
import generation.springhospital.repositories.DocumentoRepository;
import generation.springhospital.repositories.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@Service
public class GoogleCloudStorageService {


    //Variables de entorno y dependencias con las que trabajará el service Google Cloud
    @Value("${bucket.name}")
    private String bucketName;

    //Interfaz Storage de la API de GCP
    private final Storage storage;
    //Esta constante debe contener la ruta al json generado en GCP
    private final String SERVICE_ACCOUNT_JSON_PATH = "ruta a clave.json generada en GCP";

    @Autowired
    private DocumentoRepository documentoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private CitaRepository citaRepository;

    //Bloque anónimo donde seteamos a la instancia de Storage, nuestras credenciales
    {
        try {
            storage = StorageOptions.newBuilder()
                    //Setear credenciales del storage a partir de la clave Json de GCP
                    .setCredentials(ServiceAccountCredentials.fromStream(new FileInputStream(SERVICE_ACCOUNT_JSON_PATH)))
                    .build().getService();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    // Método para subir un documento asociado a un paciente
    public String uploadFileForPaciente(Long pacienteId, MultipartFile file) throws IOException {

        //Validamos la existencia del paciente
        Paciente paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));

        //Creamos una variable que lleva el nombre del archivo y la ruta con las carpetas que se crearán
        String filePath = "pacientes/" + pacienteId + "/documentos/" + file.getOriginalFilename();

        //Un objeto en GCP es representado con la clase Blob, se identifican mediante BlobId que contiene el nombre dell bucket y la ruta del archivo
        BlobId blobId = BlobId.of(bucketName, filePath);

        //BlobInfo, además del BlobId contiene los metadatos del objeto como la extensión del archivo
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(file.getContentType()).build();

        //Crea el nuevo Blob de nuestro documento
        storage.create(blobInfo, file.getBytes());

        // Guardar información del archivo en la base de datos
        Documento documento = Documento.builder()
                .nombreArchivo(file.getOriginalFilename())
                .url(filePath)
                .paciente(paciente)
                .build();
                new Documento();

        documentoRepository.save(documento);

        //Retornamos un String indicando que el archivo se subió exitosamente
        return String.format("File %s uploaded to bucket %s as %s", file.getOriginalFilename(), bucketName, blobId.getName());
    }

    // Método para subir un archivo asociado a una cita, sucede de la misma manera que con paciente
    public String uploadFileForCita(Long citaId, MultipartFile file) throws IOException {

        //Validamos la existencia de la cita
        Cita cita = citaRepository.findById(citaId)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));

        String filePath = "citas/" + citaId + "/documentos/" + file.getOriginalFilename();
        BlobId blobId = BlobId.of(bucketName, filePath);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType(file.getContentType()).build();
        storage.create(blobInfo, file.getBytes());

        // Guardar información del archivo en la base de datos
        Documento documento = Documento.builder()
                .nombreArchivo(file.getOriginalFilename())
                .url(filePath)
                .cita(cita)
                .build();
                new Documento();


        documentoRepository.save(documento);

        return String.format("File %s uploaded to bucket %s as %s", file.getOriginalFilename(), bucketName, blobId.getName());
    }


    // Método para descargar un archivo
    // En Java, el archivo es representado a través de un array de bytes
    public byte[] downloadFile(String fileName) {
        //Creamos una instancia de Blob(un archivo en GCP)
        //Lo inicializamos a partir del método get de la interfaz Storage, pasándole el BlobId
        Blob blob = storage.get(BlobId.of(bucketName, fileName));

        //Método de la instancia de Blob que permite obtener el array de bytes que representa un objeto
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
        //Creamos una instancia de Blob(un archivo en GCP) y lo inicializamos a partir del archivo que busquemos gracias al BlobId
        BlobId blobId = BlobId.of(bucketName, fileName);
        //Llamamos al método delete de la interfaz Storage que recibe el BlobId
        boolean deleted = storage.delete(blobId);

        //Mensaje a retornar a través de operador ternario
        return deleted ? "Documento borrado exitosamente" : "Documento no encontrado";
    }
}