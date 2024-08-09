package com.unigpt.chat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import com.unigpt.chat.utils.SetProxy;

@SpringBootApplication
@EnableFeignClients
public class UniGptChatServiceApplication {

	public static void main(String[] args) {
		SetProxy.setProxy();
		SpringApplication.run(UniGptChatServiceApplication.class, args);
	}

}
