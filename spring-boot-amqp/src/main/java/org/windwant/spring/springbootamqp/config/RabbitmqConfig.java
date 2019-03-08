package org.windwant.spring.springbootamqp.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Administrator on 19-3-8.
 */
@Configuration
public class RabbitmqConfig {

    final static String message = "topic.message";

    @Bean
    public Queue queue1(){
        return new Queue("queue1");
    }

    @Bean
    public Queue queue2(){
        return new Queue("queue2");
    }

    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange("topicExchange");
    }

    @Bean
    public Binding binding1(Queue queue1, TopicExchange topicExchange){
        return BindingBuilder.bind(queue1).to(topicExchange).with("route_queue1");
    }

    @Bean
    public Binding binding2(Queue queue2, TopicExchange topicExchange){
        return BindingBuilder.bind(queue2).to(topicExchange).with("route_queue2");
    }
}
