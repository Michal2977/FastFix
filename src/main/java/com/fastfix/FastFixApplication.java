package com.fastfix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class FastFixApplication {

	public static void main(String[] args) {
		SpringApplication.run(FastFixApplication.class, args);
	}

}
