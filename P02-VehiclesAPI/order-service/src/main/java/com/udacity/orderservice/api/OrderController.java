package com.udacity.orderservice.api;

import com.udacity.orderservice.domain.order.Order;
import com.udacity.orderservice.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }


    /**
     * Posts information to create a new order in the system.
     * @param order A new vehicle to add to the system.
     * @return response that the new order was added to the system
     * @throws URISyntaxException if the request contains invalid fields or syntax
     */
    @PostMapping
    ResponseEntity<Order> post(@Valid @RequestBody Order order) throws URISyntaxException {
        /**
         * TODO: Use the `save` method from the Order Service to save the input order.
         */

        Order savedOrder = orderService.save(order);
//        Order savedOrder = new Order(1, "PENDING", "DEBIT");

        return new ResponseEntity<Order>(savedOrder, HttpStatus.CREATED);
    }


}
