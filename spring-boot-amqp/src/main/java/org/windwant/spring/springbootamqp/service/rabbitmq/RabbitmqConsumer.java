package org.windwant.spring.springbootamqp.service.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 19-3-8.
 */
@Component
@RabbitListener(queues = {"queue1", "queue2"})
public class RabbitmqConsumer {

    @RabbitHandler
    public void consume(String msg){
        System.out.println("consume: " + msg);
    }
}
