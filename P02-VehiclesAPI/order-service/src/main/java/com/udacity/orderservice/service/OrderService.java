package com.udacity.orderservice.service;

import com.udacity.orderservice.domain.order.Order;
import com.udacity.orderservice.domain.order.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }


    /**
     * Either creates or updates an order, based on prior existence of order
     * @param order An order object, which can be either new or existing
     * @return the new/updated order is stored in the repository
     */
    public Order save(Order order) {

        if (order.getOrderId() != null && order.getOrderId() != 0L ) {
            return orderRepository.findById(order.getOrderId())
                    .map(orderToBeUpdated -> {
                        orderToBeUpdated.setComment(order.getComment() );
                        orderToBeUpdated.setOrderStatus(order.getOrderStatus());
                        orderToBeUpdated.setDeliveryDate( order.getDeliveryDate() );
                        return orderRepository.save(orderToBeUpdated);
                    }).orElseThrow(OrderNotFoundException::new);
        }

        return orderRepository.save(order);

    }

    /**
     * Gets order information by ID (or throws exception if non-existent)
     * @param id the ID number of the order to gather information on
     * @return the requested order's information
     */
    public Order findById(Long id) {
        /**
         * TODO: Find the order by ID from the `orderRepository` if it exists.
         *   If it does not exist, throw a OrderNotFoundException
         */
        Optional<Order> optionalOrder = orderRepository.findById(id);
        Order car = optionalOrder.orElseThrow(OrderNotFoundException::new);

        return car;
    }

    /**
     * Gathers a list of all orders
     * @return a list of all orders in the OrderRepository
     */
    public List<Order> list() {
        return orderRepository.findAll();
    }
}
