package com.udacity.pricing;

import com.udacity.pricing.domain.price.Price;
import com.udacity.pricing.domain.price.PriceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

/**
 * Creates a Spring Boot Application to run the Pricing Service.
 * TODO: Convert the application from a REST API to a microservice.
 */
@SpringBootApplication
public class PricingServiceApplication implements CommandLineRunner{

    @Autowired
    PriceRepository priceRepository;

    private static final Logger logger = LoggerFactory.getLogger(PricingServiceApplication.class);

    public static void main(String[] args) {
        logger.info("STARTING APPLICATION");
        SpringApplication.run(PricingServiceApplication.class, args);
        logger.info("APPLICATION FINISHED");

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

        logger.info("EXECUTING : command line runner");
        priceRepository.saveAll(PRICES.values());
        logger.info("hello");
    }
}
