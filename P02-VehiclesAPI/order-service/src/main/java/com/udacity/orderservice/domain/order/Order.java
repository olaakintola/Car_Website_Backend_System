package com.udacity.orderservice.domain.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;
import com.udacity.orderservice.domain.*;
import com.udacity.orderservice.domain.car.Car;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue
    private Long orderId;

    @NotNull
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @NotNull
    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    @NotNull
    @Enumerated(EnumType.STRING)
    private StoreOutlet storeOutlet;

    private BigDecimal invoiceAmount;

    @CreatedDate
    private LocalDateTime orderTime;

    private String comment;

    @Embedded
    private Customer customer = new Customer();

    private String billingAddress;

    @JsonFormat(pattern="dd-MM-yyyy")
    private Date deliveryDate;

    @Embedded
    private Car car = new Car();

    public Order() {
    }

    public Order(Long orderId, OrderStatus orderStatus, PaymentType paymentType, StoreOutlet storeOutlet, BigDecimal invoiceAmount,
                 LocalDateTime orderTime, String comment, Customer customer, String billingAddress, Date deliveryDate, Car car) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
        this.paymentType = paymentType;
        this.storeOutlet = storeOutlet;
        this.invoiceAmount = invoiceAmount;
        this.orderTime = orderTime;
        this.comment = comment;
        this.customer = customer;
        this.billingAddress = billingAddress;
        this.deliveryDate = deliveryDate;
        this.car = car;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public StoreOutlet getStoreOutlet() {
        return storeOutlet;
    }

    public void setStoreOutlet(StoreOutlet storeOutlet) {
        this.storeOutlet = storeOutlet;
    }

    public BigDecimal getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(BigDecimal invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(LocalDateTime orderTime) {
        this.orderTime = orderTime;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
}
