package com.studentmisportal.bonafide_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class BonafideServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BonafideServiceApplication.class, args);
	}

}
