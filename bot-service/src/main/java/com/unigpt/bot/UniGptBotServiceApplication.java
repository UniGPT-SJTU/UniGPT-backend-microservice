package com.unigpt.bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class UniGptBotServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UniGptBotServiceApplication.class, args);
	}

}
