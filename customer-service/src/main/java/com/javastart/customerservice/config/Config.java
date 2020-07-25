package com.javastart.customerservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@EnableRabbit
@Configuration
@PropertySources({
        @PropertySource("classpath:application.yaml")
})
public class Config {


    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port:5672}")
    private int port;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host, port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);


        return connectionFactory;
    }

    @Bean(name = "pimAmqpAdmin")
    public AmqpAdmin pimAmqpAdmin(@Autowired ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

//    @Bean
//    public RabbitTemplate rabbitTemplate() {
//        return new RabbitTemplate(connectionFactory());
//    }

    /**
     * Required for executing adminstration functions against an AMQP Broker
     */
//    @Bean
//    public RabbitAdmin amqpAdmin() {
//        return new RabbitAdmin(connectionFactory());
//    }

//    @Bean
//    public ConnectionFactory rabbitConnectionFactory() {
//        return new CachingConnectionFactory();
//    }
//
//    @Bean
//    public RabbitAdmin rabbitAdmin(@Autowired RabbitTemplate rabbitTemplate) {
//        return new RabbitAdmin(rabbitTemplate);
//    }

}
