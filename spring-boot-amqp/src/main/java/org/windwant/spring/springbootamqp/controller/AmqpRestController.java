package org.windwant.spring.springbootamqp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.windwant.spring.springbootamqp.service.rabbitmq.RabbitmqProducer;

/**
 * Created by Administrator on 19-3-8.
 */
@RestController
public class AmqpRestController {

    @Autowired
    private RabbitmqProducer rabbitmqProducer;

    @RequestMapping("/rabbit")
    public String rabbitSend(@RequestParam("num") int num){
        for (int i = 1; i < num + 1; i++) {
            if(i%2 == 0) {
                rabbitmqProducer.send("topicExchange", "route_queue2", i + "--test: " + Math.random());
            }else {
                rabbitmqProducer.send("topicExchange", "route_queue1", i + "--test: " + Math.random());
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return "TEST rabbitSend";
    }
}
