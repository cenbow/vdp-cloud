package com.vdp.sc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages= {"com.vdp.core", "com.vdp"})
public class SCApplication {

    public static void main(String[] args) {
        SpringApplication.run(SCApplication.class, args);
//        String url="http://localhost:8080/rest/order/order_123_666_888";
    }
}
