package com.virtusa.jsm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class JsmApplication {

	public static void main(String[] args) {
		SpringApplication.run(JsmApplication.class, args);
	}
	
	@Bean
	public RestTemplate geRestTemplate() {
		return new RestTemplate();
	}
}
