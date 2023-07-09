package com.devtalk.consultation.consultationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ConsultationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsultationServiceApplication.class, args);
	}

}
