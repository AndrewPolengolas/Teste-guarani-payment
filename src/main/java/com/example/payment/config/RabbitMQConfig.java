package com.example.payment.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_BOLETO = "queue.boleto";
    public static final String QUEUE_PIX = "queue.pix";
    public static final String QUEUE_CREDITO = "queue.credito";
    public static final String QUEUE_ORDER_PROCESSED = "queue.order.processed";
    public static final String EXCHANGE = "paymentExchange";

    @Bean
    public Queue boletoQueue() {
        return new Queue(QUEUE_BOLETO, true);
    }

    @Bean
    public Queue pixQueue() {
        return new Queue(QUEUE_PIX, true);
    }

    @Bean
    public Queue creditoQueue() {
        return new Queue(QUEUE_CREDITO, true);
    }

    @Bean
    public Queue orderProcessedQueue() {
        return new Queue(QUEUE_ORDER_PROCESSED, true);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Binding boletoBinding(Queue boletoQueue, TopicExchange exchange) {
        return BindingBuilder.bind(boletoQueue).to(exchange).with("payment.boleto");
    }

    @Bean
    public Binding pixBinding(Queue pixQueue, TopicExchange exchange) {
        return BindingBuilder.bind(pixQueue).to(exchange).with("payment.pix");
    }

    @Bean
    public Binding creditoBinding(Queue creditoQueue, TopicExchange exchange) {
        return BindingBuilder.bind(creditoQueue).to(exchange).with("payment.credito");
    }

    @Bean
    public Binding orderProcessedBinding(Queue orderProcessedQueue, TopicExchange exchange) {
        return BindingBuilder.bind(orderProcessedQueue).to(exchange).with("order.processed");
    }

    @Bean
    public MessageConverter messageConverter() {
        ObjectMapper objectMapper = new ObjectMapper()
                .findAndRegisterModules()
                .setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ"));
        return new Jackson2JsonMessageConverter(objectMapper);
    }


    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }
}
