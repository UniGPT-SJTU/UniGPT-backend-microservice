package com.unigpt.plugin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class UniGptPluginServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UniGptPluginServiceApplication.class, args);
	}

}
