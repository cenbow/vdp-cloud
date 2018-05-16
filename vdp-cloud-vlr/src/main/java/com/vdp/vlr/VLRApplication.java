package com.vdp.vlr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages= {"com.vdp.core", "com.vdp.vlr"})
public class VLRApplication {

    public static void main(String[] args) {
        SpringApplication.run(VLRApplication.class, args);
    }
}
