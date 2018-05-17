package com.vdp.core.tools.rabbitmq.utils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Spliterators;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.google.common.base.Splitter;
import com.vdp.core.tools.rabbitmq.config.RabbitProps;

/**
 * rabbitmq工具类,通过application中rabbitmq部分动态创建和绑定队列
 * @author longxn
 */
@Component
@ConditionalOnExpression("${container_config.transfer.enabled}")
@ConditionalOnProperty(name = "container_config.transfer.tool", havingValue = "rabbitmq")
public class RabbitmqUtil {

	@Autowired
	private RabbitAdmin rabbitAdmin;
	@Autowired
	private RabbitProps rabbitProps;
	@Autowired
	private RabbitmqFactory rabbitmqFactory;
	
	/**
	 * 在spring加载的时候进行初始化
	 */
	public RabbitmqUtil() {
		init();
	}

	/**
	 * topic类型的绑定
	 * 
	 * @param queue
	 * @param exchange
	 * @param routeKey
	 */
	public void bindingTopic(Queue queue, TopicExchange exchange, String routeKey) {
		rabbitAdmin.declareBinding(BindingBuilder.bind(queue).to(exchange).with(routeKey));
	}

	/**
	 * fanout类型的绑定, fanout是广播形式, 故无需绑定routeKey
	 * 
	 * @param queue
	 * @param exchange
	 * @param routeKey
	 */
	public void bindingFanout(Queue queue, FanoutExchange exchange, String routeKey) {
		rabbitAdmin.declareBinding(BindingBuilder.bind(queue).to(exchange));
	}

	/**
	 * 绑定队列
	 * @param exchangeType
	 * @param exchange
	 * @param routingKey
	 * @param queueName
	 */
	public void binding(String exchangeType, String exchange, String routingKey, String queueName) {
		switch (exchangeType) {
		case ExchangeTypeConst.TOPIC:
			this.bindingTopic(rabbitmqFactory.newQueue(queueName), rabbitmqFactory.newTopicExchange(exchange),
					routingKey);
			break;
		case ExchangeTypeConst.FANOUT:
			this.bindingFanout(rabbitmqFactory.newQueue(queueName), rabbitmqFactory.newFanoutExchange(exchange),
					routingKey);
			break;
		case ExchangeTypeConst.DIRECT:// direct无需绑定
			break;
		}
	}

	/**
	 * 通过application配置文件初始化队列
	 */
	public void init() {
		Map<String, String> queueNameMap = rabbitProps.getQueueNames();
		System.out.println(">>>>>>>>初始化rabbitmq");
		Collection<String> queueName = queueNameMap.values();
		for (String q : queueName) {
			List<String> queueInfo = Splitter.on(".").limit(3).trimResults().splitToList(q);
			this.binding(queueInfo.get(0), queueInfo.get(1), queueInfo.get(2), q);
		}
	}
}
