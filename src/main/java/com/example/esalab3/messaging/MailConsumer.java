package com.example.esalab3.messaging;

import com.example.esalab3.entity.ChangeLog;
import com.example.esalab3.messaging.utils.ChangeLogWithTypeInfo;
import com.example.esalab3.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequiredArgsConstructor
@Component
public class MailConsumer {

    @Value("${spring.mail.username}")
    private String senderMail;

    private final NotificationRepository notificationRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(MailConsumer.class);

    private final JavaMailSender javaMailSender;

    public void sendNotificationEmail(ChangeLog changeLog, String recipient, String sender) {
        SimpleMailMessage emailMessage = new SimpleMailMessage();
        emailMessage.setTo(recipient);
        emailMessage.setFrom(sender);
        emailMessage.setSubject("Delete Notification");
        emailMessage.setText(changeLog.toString());
        javaMailSender.send(emailMessage);
    }

    @RabbitListener(queues = {"${rabbitmq.queue.mail}"})
    public void consumeMail(ChangeLogWithTypeInfo changeLogWithTypeInfo){
        for (String to: notificationRepository.findEmailsByNotification(changeLogWithTypeInfo.getChangeLogTypeInfo())){
            sendNotificationEmail(changeLogWithTypeInfo.getChangeLog(), to, senderMail);
            LOGGER.info(String.format("Mail send to: %s", to));
        }
    }
}
