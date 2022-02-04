package student.aschm22s.hbrsiCalGenerator.email.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import student.aschm22s.hbrsiCalGenerator.calenderExport.service.CalenderExportService;
import student.aschm22s.hbrsiCalGenerator.stundenplanSpecific.veranstaltung.domain.VeranstaltungsIdsAndEmailDTO;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@Service
public class EmailSendingService {

    private final String emailUsername;
    private final JavaMailSender emailSender;
    private final CalenderExportService calenderExportService;

    public EmailSendingService(
            @Value("${ical.mail.username}") String emailUsername,
            JavaMailSender emailSender,
            CalenderExportService calenderExportService) {
        this.emailUsername = emailUsername;
        this.emailSender = emailSender;
        this.calenderExportService = calenderExportService;
    }

    public String getCalenderOverEmail(@RequestBody VeranstaltungsIdsAndEmailDTO veranstaltungsIdsAndEmailDTO) {
        MimeMessage message = emailSender.createMimeMessage();

        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(emailUsername);
            helper.setTo(veranstaltungsIdsAndEmailDTO.getEmail());
            helper.setSubject("Dein Kalender");
            helper.setText("Hier ist dein Kalender :)");

            helper.addAttachment("Calendar.ics", new ByteArrayResource(calenderExportService.createCalender(veranstaltungsIdsAndEmailDTO)));
        } catch (MessagingException | IOException e) {
            return "Beim E-Mail zusammenstellen ist ein Fehler aufgetreten. Denke mal das ist ein Bug, würd mich freuen wenn du den mir melden würdest :)";
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        try {
            emailSender.send(message);
            return "Deine Email wurde rausgeschickt^^ Wenn du keine E-Mail bekommen hast, versuche es erneut. Vergiss aber nicht das ich dich nach zu vielen Versuchen für eine gewisse Zeit blockieren werde!";
        } catch (Exception e) {
            return "Senden fehlgeschlagen, ich denke mal deine E-Mail Adresse ist nicht korrekt.";
        }
    }
}
