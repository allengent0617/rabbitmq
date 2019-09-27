package com.allen.deadqueue.demo.listener;

import com.allen.deadqueue.demo.domain.OrdersMessage;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author : allengent@163.com
 * @date : 2019/9/27 14:37
 * description :
 */


@RabbitListener(
        queues = "#{ordersQueue.name}",
        containerFactory = "rabbitListenerContainerFactory"
)

@Slf4j
@Component
public class OrdersQueueListener {


    @RabbitHandler
    public void processOrderEvent(OrdersMessage order, Message message, Channel channel) {
        log.info("OrdersQueueListener 收到 信息 order={}", order);


        //如果订单 数量 大于100，(VIP 客户单独的队列排队。。。不跟屌丝一起排队处理。。。)  则进入另外的队列处理。。。
        if (order.getCount()>100)
        {
            /**
                 队列中的消息在以下三种情况下会变成死信
            （1）消息被拒绝（basic.reject 或者 basic.nack），并且requeue=false;
            （2）消息的过期时间到期了；
            （3）队列长度限制超过了。
             **/

            try {
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false,false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else
        {
            //todo:  处理小件业务逻辑
            log.info("deal small count ...order={}",order);
            try {
                channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

}