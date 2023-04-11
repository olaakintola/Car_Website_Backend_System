package com.udacity.pricing.domain.price;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name="pricing-service", url = "pricing-service")
public interface PriceClient{

    @RequestMapping(method = RequestMethod.GET, value = "/prices/{vehicleId}")
    List<Price> findPriceByVehicleId(@PathVariable("vehicleId") Long vehicleId);

}
