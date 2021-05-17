package com.backend.fakedb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class FakedbApplication {

	public static void main(String[] args) {
		SpringApplication.run(FakedbApplication.class, args);
	}

}
