package com.allen.deadqueue.demo.rabbit;

import com.allen.deadqueue.demo.domain.OrdersMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * @author : allengent@163.com
 * @date : 2019/6/14 13:49
 * description :
 */
@Component
@Slf4j
public class SendOrdersRabbitGateway {

    private final TopicExchange topic;
    private final RabbitTemplate template;


    @Autowired
    public SendOrdersRabbitGateway(@Qualifier("ordersTopic") TopicExchange ordersTopic, @Qualifier("ordersRabbitTemplate") RabbitTemplate template) {
        this.topic = ordersTopic;
        this.template = template;
    }

    public void send(OrdersMessage OrdersMessage) {
        log.info("发送到消息队列 {}。。。", OrdersMessage);
        template.convertAndSend(topic.getName(), Globals.ORDERS_BINDING, OrdersMessage);
    }
}
