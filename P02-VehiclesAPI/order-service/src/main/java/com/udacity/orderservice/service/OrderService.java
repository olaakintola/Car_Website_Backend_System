package com.udacity.orderservice.service;

import com.udacity.orderservice.client.VehiclesClient;
import com.udacity.orderservice.domain.car.Car;
import com.udacity.orderservice.domain.order.Order;
import com.udacity.orderservice.domain.order.OrderRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final VehiclesClient vehiclesClient;

    public OrderService(OrderRepository orderRepository, VehiclesClient vehiclesClient){
        this.orderRepository = orderRepository;
        this.vehiclesClient = vehiclesClient;
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

        Mono<Car> car = vehiclesClient.getCarPrice(order.getCar().getId() );
        String price = car.block().getPrice();

        System.out.println("is price still the same"+ price);
        System.out.println("shoudl be string"+order.getCar().getPrice() );
        order.getCar().setPrice( price );
        System.out.println("string should not be the answer now"+order.getCar().getPrice() );

        String[] splittedPrice = price.split(" ");
        Double vat = 0.1 * Double.valueOf( splittedPrice[1] );
        Double invoiceAmount = vat + Double.valueOf(splittedPrice[1]);
        order.setInvoiceAmount(new BigDecimal(invoiceAmount ).setScale(2, RoundingMode.HALF_UP) );
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

    /**
     * Deletes a given order by ID
     * @param id the ID number of the order to delete
     */
    public void delete(Long id) {
        /**
         * TODO: Find the order by ID from the `repository` if it exists.
         *   If it does not exist, throw a OrderNotFoundException
         */
        Optional<Order> optionalOrder = orderRepository.findById(id);
        Order order = optionalOrder.orElseThrow(OrderNotFoundException::new);

        /**
         * TODO: Delete the order from the repository.
         */
        orderRepository.delete(order);
    }
}
