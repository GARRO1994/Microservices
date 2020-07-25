package com.javastart.notification.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

import javax.annotation.PostConstruct;

@EnableRabbit
@Configuration
//@PropertySources({
//        @PropertySource("classpath:application.yaml")
//})
public class RabbitMQConfig {

    public static final String QUEUE_DEPOSIT = "js.deposit.notify";
    private static final String TOPIC_EXCHANGE_DEPOSIT = "js.deposit.notify.exchange";
    private static final String ROUTING_KEY_DEPOSIT = "js.deposit";

    public static final String QUEUE_TRANSFER = "js.transfer.notify";
    private static final String TOPIC_EXCHANGE_TRANSFER = "js.transfer.notify.exchange";
    private static final String ROUTING_KEY_TRANSFER = "js.transfer";

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port:5672}")
    private int port;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Autowired
    private AmqpAdmin amqpAdmin;


    @Bean
    public TopicExchange depositExchange() {
        return new TopicExchange(TOPIC_EXCHANGE_DEPOSIT);
    }

    @Bean
    public Queue queueDeposit() {
        return new Queue(QUEUE_DEPOSIT);
    }

    @Bean
    public Binding depositBinding() {
        return BindingBuilder.bind(queueDeposit())
                .to(depositExchange())
                .with(ROUTING_KEY_DEPOSIT);
    }

    @Bean
    public TopicExchange transferExchange() {
        return new TopicExchange(TOPIC_EXCHANGE_TRANSFER);
    }

    @Bean
    public Queue queueTransfer() {
        return new Queue(QUEUE_TRANSFER);
    }

    @Bean
    public Binding transferBinding() {
        return BindingBuilder.bind(queueDeposit())
                .to(depositExchange())
                .with(ROUTING_KEY_TRANSFER);
    }
//
//    @Bean
//    public ConnectionFactory connectionFactory() {
//        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host, port);
//        connectionFactory.setUsername(username);
//        connectionFactory.setPassword(password);
//
//
//        return connectionFactory;
//    }
//
//    /**
//     * Required for executing adminstration functions against an AMQP Broker
//     */
//    @Bean
//    public RabbitAdmin amqpAdmin() {
//        return new RabbitAdmin(connectionFactory());
//    }
//
//
////    @Bean
////    public ConnectionFactory rabbitConnectionFactory() {
////        return new CachingConnectionFactory();
////    }
//
//    @Bean(name = "pimAmqpAdmin")
//    public AmqpAdmin pimAmqpAdmin(@Autowired ConnectionFactory connectionFactory) {
//        return new RabbitAdmin(connectionFactory);
//    }
//
//    @PostConstruct
//    public void initRmq() {
//        amqpAdmin.declareBinding(depositBinding());
//    }
}
