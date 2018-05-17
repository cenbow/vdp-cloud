package com.vdp.core.tools.rabbitmq;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.vdp.core.tools.rabbitmq.listener.MessageListenerContainer;
import com.vdp.core.tools.rabbitmq.utils.RabbitmqFactory;

@Component    
@ConditionalOnExpression("${container_config.msgqueue.enabled}")
@ConditionalOnProperty(name = "container_config.msgqueue.tool", havingValue = "rabbitmq")
public class Consumers {
	@Autowired
	MessageListenerContainer container;
	@Autowired
	ConnectionFactory connectionFactory;
	@Autowired
	RabbitmqFactory rabbitmqFactory;
	public void receive(String queueName, AcknowledgeMode acknowledgeMode, ChannelAwareMessageListener listener){
		SimpleMessageListenerContainer listenerContainer = container.newRabbitListenerContainer(connectionFactory, rabbitmqFactory.newQueue(queueName), acknowledgeMode, listener);
		listenerContainer.start();
	}
}
