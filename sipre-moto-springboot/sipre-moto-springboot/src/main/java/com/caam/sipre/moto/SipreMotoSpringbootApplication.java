package com.caam.sipre.moto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class SipreMotoSpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(SipreMotoSpringbootApplication.class, args);
	}

}
