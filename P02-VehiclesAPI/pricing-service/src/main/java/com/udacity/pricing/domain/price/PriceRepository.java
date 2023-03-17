package com.udacity.pricing.domain.price;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;


//@RepositoryRestResource(collectionResourceRel = "services/price", path = "services/price")
@RepositoryRestResource(collectionResourceRel = "vehicle-price", path = "vehicle-price")
public interface PriceRepository extends CrudRepository<Price, Long> {
     List<Price> findPriceByVehicleId(@Param("vehicleId") Long vehicleId);
}