package com.vdp.core.tools.rabbitmq.config;
  
import java.util.List;
import java.util.Map;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
/**
 * rabbitmq配置类
 * @author longxn
 */
@Component    
@ConditionalOnExpression("${container_config.msgqueue.enabled}")
@ConditionalOnProperty(name = "container_config.msgqueue.tool", havingValue = "rabbitmq")
public class RabbitProps {  
    private String address;  
    private String username;  
    private String password;  
    private boolean publisherConfirms;  
    private String exchange;
    private Map<String,String> queueNames;
      
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
    public Map<String,String> getQueueNames() {  
        return queueNames;  
    }  
    public void setQueueNames(Map<String,String> queueNames) throws Exception {
    	if(queueNames.size() < 3){
    		throw new Exception("队列格式不正确");
    	}
        this.queueNames = queueNames;  
    }  
      
}  