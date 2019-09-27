package com.allen.deadqueue.demo.controller;


import com.allen.deadqueue.demo.domain.OrdersMessage;
import com.allen.deadqueue.demo.rabbit.SendOrdersRabbitGateway;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class OrderController {

    private  final  SendOrdersRabbitGateway sendOrdersRabbitGateway;

    public OrderController(SendOrdersRabbitGateway sendOrdersRabbitGateway) {
        this.sendOrdersRabbitGateway=sendOrdersRabbitGateway;
    }

    @PostMapping("/order")
    public  void cancelOrder(@Valid @RequestBody OrdersMessage body)
    {
        sendOrdersRabbitGateway.send(body);
    }



}
