package com.vdp.core.rabbitmq.config;
  
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;
  
@Component    
@ConfigurationProperties(prefix="spring.rabbitmq")  
@ConditionalOnProperty(name = "service_enabled.rabbitmq", havingValue = "true")
public class RabbitmqProps {  
  
    private String address;  
    private String username;  
    private String password;  
    private boolean publisherConfirms;  
    private String exchange;  
    private String queueName;
    private Map<String,String> keys;  
      
    public String getAddress() {  
        return address;  
    }  
    public void setAddress(String address) {  
        this.address = address;  
    }  
    public String getUsername() {  
        return username;  
    }  
    public void setUsername(String username) {  
        this.username = username;  
    }  
    public String getPassword() {  
        return password;  
    }  
    public void setPassword(String password) {  
        this.password = password;  
    }  
    public boolean isPublisherConfirms() {  
        return publisherConfirms;  
    }  
    public void setPublisherConfirms(boolean publisherConfirms) {  
        this.publisherConfirms = publisherConfirms;  
    }  
    public String getExchange() {  
        return exchange;  
    }  
    public void setExchange(String exchange) {  
        this.exchange = exchange;  
    }  
    public String getQueueName() {  
        return queueName;  
    }  
    public void setQueueName(String queueName) {  
        this.queueName = queueName;  
    }  
    public Map<String, String> getKeys() {  
        return keys;  
    }  
    public void setKeys(Map<String, String> keys) {  
        this.keys = keys;  
    }  
      
}  