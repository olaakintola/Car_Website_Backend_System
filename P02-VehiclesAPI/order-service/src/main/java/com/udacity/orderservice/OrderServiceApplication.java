package com.udacity.orderservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
@EnableDiscoveryClient
public class OrderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderServiceApplication.class, args);
	}

	/**
	 * Web Client for the vehicles (car) API
	 * @param endpoint where to communicate for the vehicles API
	 * @return created vehicles endpoint
	 */
	@Bean(name="vehicles")
	public WebClient webClientVehicles(@Value("${vehicles.endpoint}") String endpoint) {
		return WebClient.create(endpoint);
	}

}
