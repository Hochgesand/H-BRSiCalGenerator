package student.aschm22s.hbrsiCalGenerator.hbrsiCalGenerator.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import student.aschm22s.hbrsiCalGenerator.hbrsiCalGenerator.Models.DAOObjects.VeranstaltungsIdsAndEmail;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;

@Service
public class EmailSendingService {

    @Value("${ical.mail.username}")
    private String emailUsername;
    @Value("${ical.mail.password}")
    private String emailPassword;
    @Value("${ical.mail.host}")
    private String emailhost;

    @Autowired
    private JavaMailSender emailSender;
    @Autowired
    private CalenderGeneratorService calenderGeneratorService;

    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(emailhost);
        mailSender.setPort(587);

        mailSender.setUsername(emailUsername);
        mailSender.setPassword(emailPassword);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");

        return mailSender;
    }

    public String getCalenderOverEmail(@RequestBody VeranstaltungsIdsAndEmail veranstaltungsIdsAndEmail) {
        MimeMessage message = emailSender.createMimeMessage();

        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true);helper.setFrom(emailUsername);
            helper.setTo(veranstaltungsIdsAndEmail.getEmail());
            helper.setSubject("Dein Kalender");
            helper.setText("Hier ist dein Kalender :)");

            helper.addAttachment("Calendar.ics", new ByteArrayResource(calenderGeneratorService.createCalender(veranstaltungsIdsAndEmail)));
        } catch (MessagingException | IOException e) {
            return "Beim E-Mail zusammenstellen ist ein Fehler aufgetreten. Denke mal das ist ein Bug, würd mich freuen wenn du den mir melden würdest :)";
        }

        try {
            emailSender.send(message);
            return "Deine Email wurde rausgeschickt^^ Wenn du keine E-Mail bekommen hast, versuche es erneut. Vergiss aber nicht das ich dich nach zu vielen Versuchen für eine gewisse Zeit blockieren werde!";
        } catch (Exception e) {
            return "Senden fehlgeschlagen, ich denke mal deine E-Mail Adresse ist nicht korrekt.";
        }
    }
}
