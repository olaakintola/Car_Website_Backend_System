package com.udacity.pricing.domain.price;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@RepositoryRestResource(collectionResourceRel = "vehicle-price", path = "vehicle-price")
public interface PriceRepository extends CrudRepository<Price, Long> {

}