package com.caam.sipre.carro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class SipreCarroSpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(SipreCarroSpringbootApplication.class, args);
	}

}
