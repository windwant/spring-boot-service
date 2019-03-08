package org.windwant.spring.springbootamqp.service.rabbitmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 19-3-8.
 */
@Component
public class RabbitmqProducer {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void send(String exchange, String queue, String msg){
        amqpTemplate.convertAndSend(exchange, queue, msg);
        System.out.println("send: queue: " + queue + " msg: " + msg);
    }
}
