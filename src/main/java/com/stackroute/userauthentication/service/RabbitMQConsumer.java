package com.stackroute.userauthentication.service;


import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;



//@Component
public class RabbitMQConsumer {

    @RabbitListener(queues = "${batman.rabbitmq.queue}")
    public void recievedMessage(String s) {
        System.out.println("################################Recieved Message From RabbitMQ: " + s);
    }
}