package ru.rtu_mirea.course_work_spring.Model;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/** The class that implements the logic of alerting users by their email address. **/
@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String username;

    /**
     * A method that ensures the message is sent to the user's email address.
     * @param mailTo - email address the message will be sent to.
     * @param subject - subject of the email that will be specified.
     * @param message - the main text of the message.
     **/
    public void send(String mailTo, String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(username);
        mailMessage.setTo(mailTo);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        mailSender.send(mailMessage);
    }
}
