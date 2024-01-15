package com.example.esalab3.messaging;


import com.example.esalab3.entity.ChangeLog;
import com.example.esalab3.messaging.utils.ChangeLogWithTypeInfo;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public class Producer {

    private final RabbitTemplate rabbitTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(Producer.class);

    @Value("${rabbitmq.exchange}")
    private String exchange;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    @Value("${rabbitmq.routing.key.mail}")
    private String routingKeyMail;

    @Autowired
    public Producer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void produce(ChangeLog changeLog){
        rabbitTemplate.convertAndSend(exchange, routingKey, changeLog);
        if(changeLog.getChangeType() == "delete"){
            rabbitTemplate.convertAndSend(exchange, routingKeyMail, new ChangeLogWithTypeInfo(changeLog, changeLog.getChangeType()));
            LOGGER.info(String.format("Send mail: delete notification"));
        }
        LOGGER.info(String.format("Send Change : %s", changeLog.toString()));
    }
}
