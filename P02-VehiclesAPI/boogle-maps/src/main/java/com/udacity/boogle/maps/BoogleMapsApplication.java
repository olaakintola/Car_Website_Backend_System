package com.udacity.boogle.maps;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@OpenAPIDefinition(info = @Info( title = "Maps API", version = "1.0", description = "This is a Maps API server created using springdocs - a library for OpenAPI 3 with spring boot.",
		license = @License(name = "Apache 2.0", url = "http://foo.bar"),
		contact = @Contact(url = "https://ie.linkedin.com/in/olanipekun-akintola-6746731b7", name = "OllaOla", email = "ola@gmail.com") ) )
public class BoogleMapsApplication {

	public static void main(String[] args) {
		SpringApplication.run(BoogleMapsApplication.class, args);
	}

}
