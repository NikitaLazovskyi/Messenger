package com.messageservice.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Slf4j
@Configuration
public class RabbitConfiguration {

    @Value("${rabbit.host}")
    private String amqpHost;

    @Bean
    public ConnectionFactory connectionFactory() {
        return new CachingConnectionFactory(amqpHost);
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(connectionFactory());
    }

    @Bean
    public Queue userUpdateQueue() {
        return new Queue("userUpdateQueue");
    }

    @Bean
    public Queue userRegisterQueue() {
        return new Queue("userRegisterQueue");
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("topic-exchanger");
    }

    @Bean
    public Binding bindingUpdate() {
        return BindingBuilder.bind(userUpdateQueue()).to(topicExchange()).with("user.update");
    }

    @Bean
    public Binding bindingRegister() {
        return BindingBuilder.bind(userRegisterQueue()).to(topicExchange()).with("user.register");
    }
}
