package com.allen.deadqueue.demo.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * @author : allengent@163.com
 * @date : 2019/9/27 15:08
 * description :
 */
@Data
public class OrdersMessage implements Serializable{
    private Long orderId;
    private  int count;
}
