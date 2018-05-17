package com.vdp.core.tools.rabbitmq;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.google.common.base.Splitter;
import com.google.common.collect.Maps;
import com.vdp.core.tools.rabbitmq.config.RabbitProps;
/**
 * 生产者工具类
 * @author longxn
 *
 */
@Component
@ConditionalOnExpression("${container_config.transfer.enabled}")
@ConditionalOnProperty(name = "container_config.transfer.tool", havingValue = "rabbitmq")
public class Producer {
	
	@Autowired
	private RabbitProps rabbitProps;
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	public void sender(Object msg){
		Map<String, String> queueNameMap = rabbitProps.getQueueNames();
		System.out.println(">>>>>>>>初始化rabbitmq");
		String q = MapUtils.getString(queueNameMap, "transferQ");
		List<String> queueInfo = Splitter.on(".").limit(3).trimResults().splitToList(q);
		senderByType(queueInfo.get(0), queueInfo.get(1), queueInfo.get(2), msg);
	}
	
	public void senderByType(String exchangeType, String exchange, String routingKey, Object msg) {
		switch (exchangeType) {
		case ExchangeTypeConst.TOPIC:
			sendTopicMsg(exchange,routingKey,msg);
			break;
		case ExchangeTypeConst.FANOUT:
			sendFanoutMsg(exchange,"",msg);
			break;
		case ExchangeTypeConst.DIRECT:// direct无需绑定
			sendDirectMsg(routingKey,msg);
			break;
		}
	}
	
	public void sendDirectMsg(String routingKey, Object msg) {
		//发生消息,direct模式采用默认的exchange
		rabbitTemplate.convertAndSend(routingKey, msg);
	}
	
	public void sendTopicMsg(String exchange, String routingKey, Object msg){
		//发送消息, exchange是"exchange"且route key是"topic.message"或者"topic.*"或者topic.#"的能接收到消息
		rabbitTemplate.convertAndSend(exchange, routingKey, msg);
	}
	
	public void sendFanoutMsg(String exchange, String routingKey, Object msg){
		//发送消息, route key可以设置为"",因为是Fanout模式,所以没有意义,就算配置也会被忽略
		rabbitTemplate.convertAndSend(exchange, routingKey, msg);
	}
}
