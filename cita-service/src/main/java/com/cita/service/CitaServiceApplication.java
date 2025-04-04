package com.cita.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CitaServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CitaServiceApplication.class, args);
	}

}
