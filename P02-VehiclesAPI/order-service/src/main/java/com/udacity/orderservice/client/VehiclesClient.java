package com.udacity.orderservice.client;

import com.udacity.orderservice.domain.car.Car;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * Implements a class to interface with the Car Client for car data.
 */
@Component
public class VehiclesClient {

    private static final Logger log = LoggerFactory.getLogger(VehiclesClient.class);

    private final WebClient client;

    public VehiclesClient(WebClient vehicles) {
        this.client = vehicles;
    }

    /**
     * Gets a car price from the Vehicles client, given id.
     * @param carId A car id
     * @return An updated car information including price,
     *   or an exception message noting the Vehicles service is down
     */
    public Mono<Car> getCarPrice(Long carId) {

        try {
            Mono<Car> car = client
                    .get()
                    .uri("/cars/"+ carId)
                    .retrieve().bodyToMono(Car.class);

            System.out.println("inside webclient" + car.block().getPrice() );
            return car;
        } catch (Exception e) {
            log.warn("Vehicle service is down");
            return null;
        }
    }

}
