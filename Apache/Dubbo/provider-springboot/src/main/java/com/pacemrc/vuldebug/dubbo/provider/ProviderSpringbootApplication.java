package com.pacemrc.vuldebug.dubbo.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProviderSpringbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProviderSpringbootApplication.class, args);
        System.out.println("dubbo service started");
    }

}
