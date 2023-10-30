package com.caam.sipre.eurekaservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class SipreEurekaserviceSpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(SipreEurekaserviceSpringbootApplication.class, args);
	}

}
