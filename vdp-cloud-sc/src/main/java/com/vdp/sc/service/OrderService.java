package com.vdp.sc.service;

import java.util.UUID;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.vdp.core.rabbitmq.config.RabbitmqProps;
  
@Service("orderService")  
public class OrderService implements RabbitTemplate.ConfirmCallback {  
      
    @Value("${spring.rabbitmq.keys.orderBounding}")  
    private String boundingKey;  
      
    @Value("${spring.rabbitmq.exchange}")  
    private String testExchange;  
      
    @Autowired  
    private RabbitmqProps rabbitmqProps;
    
    @Autowired
    private RabbitTemplate rabbitTemplate;  
  
    /** 
     * 发布确认机制，生产者发送消息后的回调.也就是是否可路由 
     */  
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {  
  
        System.out.println(" 回调id:" + correlationData);    
        if (ack) {    
            System.out.println("订单发送成功！");    
        } else {    
            System.out.println("订单发送失败，进行重新发送" + cause);    
        }    
          
    }  
  
    @Autowired    
    public OrderService(RabbitTemplate rabbitTemplate) {    
        this.rabbitTemplate = rabbitTemplate;    
        rabbitTemplate.setConfirmCallback(this);   
    }    
      
    public void saveOrder(String id){  
        rabbitTemplate.setRoutingKey(rabbitmqProps.getKeys().get("orderRouting"));  
        CorrelationData correlationId = new CorrelationData(UUID.randomUUID().toString());  
        rabbitTemplate.convertAndSend(testExchange, boundingKey, id, correlationId);  
    }  
      
}  