package com.vdp.core.tools.rabbitmq;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.collections.MapUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.google.common.base.Splitter;
import com.vdp.core.tools.rabbitmq.config.RabbitProps;
import com.vdp.core.tools.rabbitmq.utils.ExchangeTypeConst;
/**
 * 生产者工具类
 * @author longxn
 *
 */
@Component
@ConditionalOnExpression("${container_config.msgqueue.enabled}")
@ConditionalOnProperty(name = "container_config.msgqueue.tool", havingValue = "rabbitmq")
public class Producers {
	
	@Autowired
	private RabbitProps rabbitProps;
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	public void sender(Object msg, String queueKey){
		Map<String, String> queueNameMap = rabbitProps.getQueueNames();
		String uuid = UUID.randomUUID().toString();//消息id
		System.out.println(">>>>>>>>初始化rabbitmq");
		String q = MapUtils.getString(queueNameMap, queueKey);
		List<String> queueInfo = Splitter.on(".").limit(3).trimResults().splitToList(q);
		CorrelationData correlationId = new CorrelationData(uuid);
		senderByType(queueInfo.get(0), queueInfo.get(1), queueInfo.get(2), msg, correlationId);
	}
	
	public void senderByType(String exchangeType, String exchange, String routingKey, Object msg, CorrelationData correlationId) {
		switch (exchangeType) {
		case ExchangeTypeConst.TOPIC:
			sendTopicMsg(exchange,routingKey,msg,correlationId);
			break;
		case ExchangeTypeConst.FANOUT:
			sendFanoutMsg(exchange,"",msg,correlationId);
			break;
		case ExchangeTypeConst.DIRECT:// direct无需绑定
			sendDirectMsg(routingKey,msg,correlationId);
			break;
		}
	}
	
	public void sendDirectMsg(String routingKey, Object msg, CorrelationData correlationId) {
		//发生消息,direct模式采用默认的exchange
		rabbitTemplate.convertAndSend(routingKey, msg, correlationId);
	}
	
	public void sendTopicMsg(String exchange, String routingKey, Object msg, CorrelationData correlationId){
		//发送消息, exchange是"exchange"且route key是"topic.message"或者"topic.*"或者topic.#"的能接收到消息
		rabbitTemplate.convertAndSend(exchange, routingKey, msg,correlationId);
	}
	
	public void sendFanoutMsg(String exchange, String routingKey, Object msg, CorrelationData correlationId){
		//发送消息, route key可以设置为"",因为是Fanout模式,所以没有意义,就算配置也会被忽略
		rabbitTemplate.convertAndSend(exchange, routingKey, msg,correlationId);
	}
}
