package student.aschm22s.hbrsiCalGenerator.hbrsiCalGenerator.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class
EmailSendingConfiguration {

    private final String emailUsername;
    private final String emailPassword;
    private final String emailhost;

    public EmailSendingConfiguration(
            @Value("${ical.mail.username}") String emailUsername,
            @Value("${ical.mail.password}") String emailPassword,
            @Value("${ical.mail.host}") String emailhost) {
        this.emailUsername = emailUsername;
        this.emailPassword = emailPassword;
        this.emailhost = emailhost;
    }

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
}
