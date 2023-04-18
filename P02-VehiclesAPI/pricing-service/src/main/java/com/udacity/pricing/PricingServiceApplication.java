package com.udacity.pricing;

import com.udacity.pricing.domain.price.Price;
import com.udacity.pricing.domain.price.PriceRepository;
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
