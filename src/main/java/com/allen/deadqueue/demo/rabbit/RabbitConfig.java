package com.allen.deadqueue.demo.rabbit;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author : allengent@163.com
 * @date : 2019/9/27 14:35
 * description :
 */
@Configuration
@EnableRabbit
public class RabbitConfig {


    private static final String[] DEFAULT_JSON_TRUSTED_PACKAGE = {
            "*"
    };

    private static MessageConverter createMessageConverter(String[] trustedPackages) {
        Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();
        DefaultClassMapper classMapper = new DefaultClassMapper();
        classMapper.setTrustedPackages(trustedPackages);
        jackson2JsonMessageConverter.setClassMapper(classMapper);
        return jackson2JsonMessageConverter;
    }

    @Bean
    public  RabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        // 需要 @EnableRabbit 注解
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(createMessageConverter(DEFAULT_JSON_TRUSTED_PACKAGE));

        //配置手工确认 或者自动确认
        factory.setAcknowledgeMode(Globals.ackMode);

        //当有10个消息 没有 ack和nack，则不再派发消息
        factory.setPrefetchCount(10);
        return factory;
    }


    @Bean
    public RabbitTemplate ordersRabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(createMessageConverter(DEFAULT_JSON_TRUSTED_PACKAGE));
        return  rabbitTemplate;
    }


    @Bean
    public TopicExchange  ordersTopic() {
        return  new TopicExchange(Globals.TOPIC_ORDERS,true,false);
    }

    /**
     * 队列上 配置 死信交换机, 注意发送到死信交换机的 routing key 与死信队列 binding key 匹配
     * @return
     */
    @Bean
    public Queue ordersQueue() {
        Map<String, Object> args = new HashMap<String, Object>();
        args.put("x-dead-letter-exchange", Globals.TOPIC_DEAD);
        args.put("x-dead-letter-routing-key",Globals.DEAD_ROUTING);
        // 配置消息 ttl
        args.put ("x-message-ttl", Globals.MSG_TIME_OUT);
        return new Queue(Globals.QUEUE_ORDERS,true,false,false,args);
    }


    @Bean
    public Binding ordersQueueBinding(TopicExchange ordersTopic, Queue ordersQueue) {
        return BindingBuilder.bind(ordersQueue)
                .to(ordersTopic)
                .with(Globals.ORDERS_BINDING);
    }




}
