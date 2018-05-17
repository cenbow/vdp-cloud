package com.vdp.core.tools.rabbitmq.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;

@Component
@ConditionalOnExpression("${container_config.msgqueue.enabled}")
@ConditionalOnProperty(name = "container_config.msgqueue.tool", havingValue = "rabbitmq")
public class RabbitmqFactory {
	
	@Autowired
	private RabbitAdmin rabbitAdmin;
	
	public Queue newQueue(String queueName) {
		/*  
         *   durable="true" 持久化 rabbitmq重启的时候不需要创建新的队列  
         auto-delete 表示消息队列没有在使用时将被自动删除 默认是false  
         exclusive  表示该消息队列是否只在当前connection生效,默认是false*/
		Queue queue = new Queue(queueName,true);
		rabbitAdmin.declareQueue(queue);
		return queue;
	}
	
	public List<Queue> newQueues(String ... queueName) {
		ArrayList<Queue> list = Lists.newArrayList();
		for (String q : queueName) {
			Queue queue = new Queue(q,true);
			list.add(queue);
			rabbitAdmin.declareQueue(queue);
		}
		return list;
	}
	
	public TopicExchange newTopicExchange(String exchange) {
		TopicExchange topicExchange = new TopicExchange(exchange,true,false);
		this.rabbitAdmin.declareExchange(topicExchange);
        return topicExchange;
    }
	
	public FanoutExchange newFanoutExchange(String exchange) {
		FanoutExchange fanoutExchange = new FanoutExchange(exchange);
		this.rabbitAdmin.declareExchange(fanoutExchange);
		return fanoutExchange;
	}
	
	
}
