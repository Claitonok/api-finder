package com.finder.api.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import com.finder.api.dto.EmailMessage;
import com.finder.api.rabbitMQ.RabbitConfig;


@Service
public class EmailConsumer {

    private final EmailService emailService;

    EmailConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @RabbitListener(queues = RabbitConfig.EMAIL_QUEUE)
    public void receive(EmailMessage msg) {

        System.out.println("Mensagem recebida da fila");

        emailService.sendRecoveryEmail(
            msg.getEmail(),
            msg.getToken()
        );
    }
}
