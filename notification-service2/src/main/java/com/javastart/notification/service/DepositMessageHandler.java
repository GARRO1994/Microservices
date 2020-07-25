package com.javastart.notification.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javastart.notification.config.RabbitMQConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class DepositMessageHandler {

    private final JavaMailSender javaMailSender;

    @Autowired
    public DepositMessageHandler(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_DEPOSIT)
    public void receive(Message message) {
        System.out.println(message);
        byte[] body = message.getBody();
        String jsonBody = new String(body);
        ObjectMapper objectMapper = new ObjectMapper();
        DepositResponseDTO depositResponseDTO = null;
        try {
            depositResponseDTO = objectMapper.readValue(jsonBody, DepositResponseDTO.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(depositResponseDTO);

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(depositResponseDTO.getEmail());
        msg.setFrom("bor@qwer.com");

        msg.setSubject("Deposit");
        msg.setText("Make deposit to bill: " + depositResponseDTO.getBillId());

        javaMailSender.send(msg);

    }
}
