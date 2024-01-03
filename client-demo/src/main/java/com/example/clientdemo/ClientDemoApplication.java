package com.example.clientdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ClientDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClientDemoApplication.class, args);
    }

}
