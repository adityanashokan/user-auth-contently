package com.stackroute.userauthentication.service;


import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;



@Service
public class RabbitMQSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Value("${batman.rabbitmq.exchange}")
    private String exchange;

    @Value("${batman.rabbitmq.routingkey}")
    private String routingkey;

    public void send(String tkn) {
        rabbitTemplate.convertAndSend(exchange, routingkey, tkn);
        System.out.println("Send msg = " + tkn);

    }
}