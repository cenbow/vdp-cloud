package com.vdp.core.tools.rabbitmq.config;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.vdp.core.tools.rabbitmq.callback.MsgSendConfirmCallBack;
import com.vdp.core.tools.rabbitmq.callback.MsgSendReturnCallback;
/**
 * Rabbit配置类
 * @author longxn
 *
 */
@Configuration
@ConditionalOnExpression("${container_config.msgqueue.enabled}")
@ConditionalOnProperty(name = "container_config.msgqueue.tool", havingValue = "rabbitmq")
public class RabbitConfig {
    
    @Value("${spring.rabbitmq.host}")
    private String addresses;
    
    @Value("${spring.rabbitmq.port}")
    private String port;

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Value("${spring.rabbitmq.virtualHost}")
    private String virtualHost;

//    @Value("${spring.rabbitmq.publisher_confirms}")
//    private boolean publisherConfirms;
    
    @Bean
    public ConnectionFactory connectionFactory() {

        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setAddresses(addresses+":"+port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setVirtualHost(virtualHost);
        /** 如果要进行消息回调，则这里必须要设置为true */
//        connectionFactory.setPublisherConfirms(publisherConfirms);
        
        return connectionFactory;
    }
    
    @Bean
    /** 因为要设置回调类，所以应是prototype类型，如果是singleton类型，则回调类为最后一次设置 */
    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
    public RabbitTemplate rabbitTemplatenew() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory());
        /**若使用confirm-callback或return-callback，必须要配置publisherConfirms或publisherReturns为true  
         * 每个rabbitTemplate只能有一个confirm-callback和return-callback*/
//        template.setConfirmCallback(msgSendConfirmCallBack());
        /**
         * 使用return-callback时必须设置mandatory为true，或者在配置中设置mandatory-expression的值为true，可针对每次请求的消息去确定’mandatory’的boolean值，  
         * 只能在提供’return -callback’时使用，与mandatory互斥*/  
        template.setReturnCallback(msgSendReturnCallback()); 
       template.setMandatory(true);  
        return template;
    }
    
    /**  
    消息确认机制  
    Confirms给客户端一种轻量级的方式，能够跟踪哪些消息被broker处理，哪些可能因为broker宕掉或者网络失败的情况而重新发布。  
    确认并且保证消息被送达，提供了两种方式：发布确认和事务。(两者不可同时使用)在channel为事务时，  
    不可引入确认模式；同样channel为确认模式下，不可使用事务。  
     */  
    @Bean  
    public MsgSendConfirmCallBack msgSendConfirmCallBack(){  
        return new MsgSendConfirmCallBack();  
    }
    
    /**
     * 消息回调机制
     * @return
     */
    @Bean  
    public MsgSendReturnCallback msgSendReturnCallback(){  
        return new MsgSendReturnCallback();  
    }
    
    
    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
    	RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
		return rabbitAdmin;
    }

}