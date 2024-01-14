package com.example.esalab3.service;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import com.example.esalab3.entity.ChangeLog;

@RequiredArgsConstructor
@Service
public class MessageService {

    private final JavaMailSender javaMailSender;

    public void sendNotificationEmail(ChangeLog changeLog,String recipient, String sender) {
        SimpleMailMessage emailMessage = new SimpleMailMessage();
        emailMessage.setTo(recipient);
        emailMessage.setFrom(sender);
        emailMessage.setSubject("Notification: ChangeLog Update");
        emailMessage.setText(changeLog.toString());
        javaMailSender.send(emailMessage);
    }
}
