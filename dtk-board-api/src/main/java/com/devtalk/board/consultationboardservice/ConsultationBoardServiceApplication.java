package com.devtalk.board.consultationboardservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ConsultationBoardServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsultationBoardServiceApplication.class, args);
    }

}
