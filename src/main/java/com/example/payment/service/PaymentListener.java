package com.example.payment.service;

import com.example.payment.domain.Order;
import com.example.payment.service.PaymentService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentListener {
    private final PaymentService paymentService;

    public PaymentListener(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @RabbitListener(queues = "queue.boleto")
    public void processBoletoPayment(Order order) {
        System.out.println("Received BOLETO order: " + order);
        paymentService.processPayment(order);
    }

    @RabbitListener(queues = "queue.pix")
    public void processPixPayment(Order order) {
        System.out.println("Received PIX order: " + order);
        paymentService.processPayment(order);
    }

    @RabbitListener(queues = "queue.credito")
    public void processCreditoPayment(Order order) {
        System.out.println("Received CREDITO order: " + order);
        paymentService.processPayment(order);
    }
}
