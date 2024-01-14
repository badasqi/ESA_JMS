package com.example.esalab3.messaging;

import com.example.esalab3.entity.ChangeLog;
import com.example.esalab3.repository.ChangeLogRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class Consumer {

    private final ChangeLogRepository changeLogRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(Consumer.class);

    @RabbitListener(queues = {"${rabbitmq.queue}"})
    public void consume(ChangeLog changeLog){
        LOGGER.info(String.format("GET Change in DB", changeLog));
        changeLogRepository.save(changeLog);
    }
}
