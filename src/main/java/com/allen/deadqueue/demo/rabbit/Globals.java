package com.allen.deadqueue.demo.rabbit;

import org.springframework.amqp.core.AcknowledgeMode;

/**
 * @author : allengent@163.com
 * @date : 2019/9/27 14:58
 * description :
 */
public class Globals {

    public  static final String TOPIC_ORDERS ="topic.order";

    public  static  final String TOPIC_DEAD="topic.dead";

    public  static  final String QUEUE_ORDERS ="queue.order";

    public  static  final String QUEUE_DEAD="queue.dead";

    public  static final String ORDERS_BINDING="binding.order.*";


    public  static final String DEAD_ROUTING="dead.order.routing.key";


    public static  final AcknowledgeMode ackMode = AcknowledgeMode.MANUAL;


    /**
     * 消息  多久没有消费 就 进入死信队列  ，单位 微秒
     */
    public static final int MSG_TIME_OUT=60*1000;
}
