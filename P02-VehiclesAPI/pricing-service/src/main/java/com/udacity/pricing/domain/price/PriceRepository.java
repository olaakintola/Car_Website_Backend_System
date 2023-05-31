package com.udacity.pricing.domain.price;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import java.math.BigDecimal;


@RepositoryRestResource(collectionResourceRel = "price", exported = true, path = "price")
public interface PriceRepository extends CrudRepository<Price, Long> {

    Price findPriceByVehicleId(@Param("vehicleId") Long vehicleId);

}