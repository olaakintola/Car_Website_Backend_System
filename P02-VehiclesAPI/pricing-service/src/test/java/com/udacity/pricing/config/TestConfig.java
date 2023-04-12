package com.udacity.pricing.config;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfig {

    @LoadBalanced
    @Bean
    TestRestTemplate testRestTemplate(){
        return new TestRestTemplate();
    }

}
