package com.allen.deadqueue.demo.listener;

import com.allen.deadqueue.demo.domain.OrdersMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author : allengent@163.com
 * @date : 2019/9/27 14:38
 * description :
 */

@RabbitListener(
        queues = "#{deadQueue.name}",
        containerFactory = "rabbitListenerContainerFactory"
)


@Slf4j
@Component
public class DeadQueueListener {


    @RabbitHandler
    public void processDeadEvent(OrdersMessage msg) {
        log.info("DeadQueueListener 收到 信息 msg={}", msg);

        //todo: ack  or nack



    }

}