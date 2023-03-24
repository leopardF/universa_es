package com.leopard.universa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UniversaApplication {

    public static void main(String[] args) {
        System.out.println("启动 Spring Boot...");
        SpringApplication.run(UniversaApplication.class, args);
    }
}
