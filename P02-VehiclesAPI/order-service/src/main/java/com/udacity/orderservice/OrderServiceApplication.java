package com.udacity.orderservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
@EnableDiscoveryClient
@OpenAPIDefinition(info = @Info( title = "Orders API", version = "1.0", description = "This is a Orders API server created using springdocs - a library for OpenAPI 3 with spring boot.",
		license = @License(name = "Apache 2.0", url = "http://foo.bar"),
		contact = @Contact(url = "https://ie.linkedin.com/in/olanipekun-akintola-6746731b7", name = "OllaOla", email = "ola@gmail.com") ) )
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
