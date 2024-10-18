package generation.springhospital.email;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailService {

    @Value("${sendgrid.api.key}")
    private String sendGridApiKey;

    @Value("${sendgrid.sender.email}")
    private String emailSender;

    public void enviarCorreo(String destinatario, String asunto, String contenido) throws IOException, IOException {

        //Instancias de Email representando el remitente y el destinatario
        Email from = new Email(emailSender);
        Email to = new Email(destinatario);

        //Contenido del correo
        Content content = new Content("text/plain", contenido);

        //Instancia de Mail que contiene los datos de envío y el contenido
        Mail mail = new Mail(from, asunto, to, content);

        //Instancia de clase SendGrid a la que pasamos la Key para que nos identifique
        SendGrid sendGrid = new SendGrid(sendGridApiKey);
        Request request = new Request();

        //Envío de la petición a la api de SendGrid con los datos del mail
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            sendGrid.api(request);
        } catch (IOException ex) {
            throw ex;
        }
    }
}