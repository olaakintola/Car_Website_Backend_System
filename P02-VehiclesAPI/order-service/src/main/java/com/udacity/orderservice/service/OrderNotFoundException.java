package com.udacity.orderservice.service;

public class OrderNotFoundException extends RuntimeException{

    public OrderNotFoundException(){}

    public OrderNotFoundException(String message) {
        super(message);
    }

}
