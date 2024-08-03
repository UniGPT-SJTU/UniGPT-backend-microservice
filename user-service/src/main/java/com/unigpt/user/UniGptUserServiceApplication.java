package com.unigpt.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class UniGptUserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UniGptUserServiceApplication.class, args);
	}

}
