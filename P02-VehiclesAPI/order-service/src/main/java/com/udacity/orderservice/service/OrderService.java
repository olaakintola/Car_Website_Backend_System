package com.udacity.orderservice.service;

import com.udacity.orderservice.domain.order.Order;
import com.udacity.orderservice.domain.order.OrderRepository;
import org.springframework.stereotype.Service;

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
}
