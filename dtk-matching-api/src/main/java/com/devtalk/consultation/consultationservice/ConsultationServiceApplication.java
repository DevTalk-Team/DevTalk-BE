package com.devtalk.consultation.consultationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class ConsultationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsultationServiceApplication.class, args);
	}

}
