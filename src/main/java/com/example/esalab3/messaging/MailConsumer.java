package com.example.esalab3.messaging;

import com.example.esalab3.entity.ChangeLog;
import com.example.esalab3.entity.Notification;
import com.example.esalab3.repository.ChangeLogRepository;
import com.example.esalab3.repository.NotificationRepository;
import com.example.esalab3.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequiredArgsConstructor
@Component
public class MailConsumer {

    @Value("${spring.mail.username}")
    private String senderMail;

    private final NotificationRepository notificationRepository;
    private final MessageService messageService;
    private static final Logger LOGGER = LoggerFactory.getLogger(MailConsumer.class);

    @RabbitListener(queues = {"${rabbitmq.queue.mail}"})
    public void consumeMail(ChangeLog changeLog){
        for (String to: notificationRepository.findAllEmails()){
            messageService.sendNotificationEmail(changeLog, to, senderMail);
            LOGGER.info(String.format("Mail send to: ", to));
        }
    }
}
