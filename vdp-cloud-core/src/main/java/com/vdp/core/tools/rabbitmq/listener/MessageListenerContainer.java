package com.vdp.core.tools.rabbitmq.listener;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnExpression("${container_config.transfer.enabled}")
@ConditionalOnProperty(name = "container_config.transfer.tool", havingValue = "rabbitmq")
public class MessageListenerContainer {
	/**
	 * MessageListenerContainer 观察 监听模式 当有消息到达时会通知监听在对应的队列上的监听对象
	 */
	public SimpleMessageListenerContainer newRabbitListenerContainer(ConnectionFactory connectionFactory, Queue queue,
			AcknowledgeMode acknowledgeMode, ChannelAwareMessageListener listener) {
		SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer(
				connectionFactory);
		simpleMessageListenerContainer.addQueues(queue);
		simpleMessageListenerContainer.setExposeListenerChannel(true);
		simpleMessageListenerContainer.setMaxConcurrentConsumers(1);
		simpleMessageListenerContainer.setConcurrentConsumers(1);
		simpleMessageListenerContainer.setAcknowledgeMode(acknowledgeMode); // 设置确认模式手工确认
		simpleMessageListenerContainer.setMessageListener(listener);
		return simpleMessageListenerContainer;
	}
}
