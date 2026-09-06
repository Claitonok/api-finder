package com.finder.api.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.finder.api.dto.EmailMessage;
import com.finder.api.rabbitMQ.RabbitConfig;


@Service
public class EmailProducer {

    private final RabbitTemplate rabbitTemplate;

    EmailProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void send(String email, String token) {

        EmailMessage message = new EmailMessage();
        message.setEmail(email);
        message.setToken(token);

        rabbitTemplate.convertAndSend(
            RabbitConfig.EXCHANGE,
            RabbitConfig.ROUTING_KEY,
            message
        );
        System.out.println("Mensagem enviada para fila: " + email + " com token: " + token);
    }

}
