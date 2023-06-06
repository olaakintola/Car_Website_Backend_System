package com.udacity.orderservice.api;

import com.udacity.orderservice.domain.order.Order;
import com.udacity.orderservice.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    /**
     * Creates a list to store any orders.
     * @return list of orders
     */
    @GetMapping
    public List<Order> getOrders() {
        return orderService.list();
    }

    /**
     * Gets information of a specific order by ID.
     * @param id the id number of the given order
     * @return all information for the requested order
     */
    @GetMapping("/{id}")
    public Order getOrderDetails(@PathVariable Long id) {
        /**
         * TODO: Use the `findById` method from the Order Service to get order information.
         */
        return orderService.findById(id);
    }


    /**
     * Posts information to create a new order in the system.
     * @param order A new vehicle to add to the system.
     * @return response that the new order was added to the system
     * @throws URISyntaxException if the request contains invalid fields or syntax
     */
    @PostMapping
    public ResponseEntity<Order> post(@Valid @RequestBody Order order) throws URISyntaxException {
        /**
         * TODO: Use the `save` method from the Order Service to save the input order.
         */
        Order savedOrder = orderService.save(order);
        return new ResponseEntity<Order>(savedOrder, HttpStatus.CREATED);
    }


    /**
     * Updates the information of a order in the system.
     * @param id The ID number for which to update order information.
     * @param order The updated information about the related vehicle.
     * @return response that the order was updated in the system
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> put(@PathVariable Long id, @Valid @RequestBody Order order) {
        /**
         * TODO: Set the id of the input order object to the `id` input.
         * TODO: Save the order using the `save` method from the Order service
         */
        order.setOrderId(id);
        Order updatedOrder = orderService.save(order);
        return ResponseEntity.ok(updatedOrder);
    }


}
