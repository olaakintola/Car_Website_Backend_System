package com.udacity.pricing.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PricingServiceIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    public void getAllVehiclePrice(){

        ResponseEntity<Resources> responseEntity = this.testRestTemplate
                .getForEntity("http://localhost:8082/api/v1/services/vehicle-price", Resources.class);

        assertThat(responseEntity.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    public void getFirstVehiclePrice(){
        ResponseEntity<Resource> responseEntity = this.testRestTemplate
                .getForEntity("http://localhost:8082/api/v1/services/vehicle-price/1", Resource.class);

        assertThat(responseEntity.getStatusCode(), equalTo(HttpStatus.OK));
    }

}
