package com.example.payment.service;

import com.example.payment.domain.Order;
import com.example.payment.domain.ProcessedOrderResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PaymentService {
    private final RabbitTemplate rabbitTemplate;

    private static final String ORDER_PROCESSED_QUEUE = "queue.order.processed";

    public PaymentService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void processPayment(Order order) {
        try {
            System.out.println("Processing payment for Order ID: " + order.getId());

            Thread.sleep(5000);

            boolean success = Math.random() > 0.5;

            if (success) {
                System.out.println("Payment successful for Order ID: " + order.getId());

            } else {
                System.out.println("Payment failed for Order ID: " + order.getId());
            }

            Date paymentDate = success ? new Date() : null;

            sendProcessedOrder(order.getId(), success, paymentDate);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Payment processing interrupted for Order ID: " + order.getId());
        }
    }

    private void sendProcessedOrder(Long orderId, boolean success, Date paymentDate) {
        ProcessedOrderResponse response = new ProcessedOrderResponse(orderId, success, paymentDate);
        try {
            String jsonMessage = new ObjectMapper().writeValueAsString(response);
            Message message = new Message(jsonMessage.getBytes(), new MessageProperties());
            rabbitTemplate.send(ORDER_PROCESSED_QUEUE, message);
            System.out.println("Sent processed order to queue with manual serialization: " + ORDER_PROCESSED_QUEUE);
        } catch (Exception e) {
            e.printStackTrace();
        }
}}
