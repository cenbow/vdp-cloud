package com.vdp.sc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vdp.sc.service.OrderService;

@RestController  
public class OrderController {  
  
    @Autowired  
    private OrderService orderService;
      
    @PostMapping(value = "/rest/order/{id}",
            produces = MediaType.TEXT_PLAIN_VALUE+";charset=UTF-8")
    public void saveOrder(@PathVariable String id,HttpServletRequest request,HttpServletResponse response){  
        orderService.saveOrder(id);  
    }  
      
}  