package com.udacity.pricing;

import com.udacity.pricing.domain.price.Price;
import com.udacity.pricing.domain.price.PriceRepository;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

/**
 * Creates a Spring Boot Application to run the Pricing Service.
 * TODO: Convert the application from a REST API to a microservice.
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@OpenAPIDefinition(info = @Info( title = "Pricing API", version = "1.0", description = "This is a Pricing API server created using springdocs - a library for OpenAPI 3 with spring boot.",
        license = @License(name = "Apache 2.0", url = "http://foo.bar"),
        contact = @Contact(url = "https://ie.linkedin.com/in/olanipekun-akintola-6746731b7", name = "OllaOla", email = "ola@gmail.com") ) )
public class PricingServiceApplication implements CommandLineRunner{

    @Autowired
    PriceRepository priceRepository;

    public static void main(String[] args) {
        SpringApplication.run(PricingServiceApplication.class, args);
    }



    /**
     * Gets a random price to fill in for a given vehicle ID.
     * @return random price for a vehicle
     */
    private static BigDecimal randomPrice() {
        return new BigDecimal(ThreadLocalRandom.current().nextDouble(1, 5))
                .multiply(new BigDecimal(5000d)).setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public void run(String... args) throws Exception {

        /**
         * Holds {ID: Price} pairings (current implementation allows for 20 vehicles)
         */
        final Map<Long, Price> PRICES = LongStream
                .range(1, 20)
                .mapToObj(i -> new Price("USD", randomPrice(), i))
                .collect(Collectors.toMap(Price::getVehicleId, p -> p));

        for (Map.Entry<Long, Price> entry : PRICES.entrySet())
        {
            System.out.println("key = " + entry.getKey() + ", value = " + entry.getValue());
        }

        priceRepository.saveAll(PRICES.values());

    }
}
