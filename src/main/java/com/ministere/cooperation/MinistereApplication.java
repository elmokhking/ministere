package com.ministere.cooperation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MinistereApplication {

	public static void main(String[] args) {
		SpringApplication.run(MinistereApplication.class, args);
	}
}
