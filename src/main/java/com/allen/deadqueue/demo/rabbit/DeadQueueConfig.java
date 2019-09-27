package com.allen.deadqueue.demo.rabbit;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author : allengent@163.com
 * @date : 2019/9/27 14:49
 * description :
 */
@Configuration
public class DeadQueueConfig {

    @Bean
    public TopicExchange deadTopic() {
        return  new TopicExchange(Globals.TOPIC_DEAD,true,false);
    }

    @Bean
    public Queue deadQueue() {
        return new Queue(Globals.QUEUE_DEAD,true,false,false,null);
    }


    @Bean
    public Binding deadQueueBinding(TopicExchange deadTopic, Queue deadQueue) {
        return BindingBuilder.bind(deadQueue)
                .to(deadTopic)
                .with(Globals.DEAD_ROUTING);
    }
}
