package com.capstone.ordermanagementservice.models;

public enum OrderStatus {
    PENDING,
    CREATED, // Order created but not yet processed
    CONFIRMED,      // Order has been confirmed by the system
    SHIPPED,        // Order has been shipped to the delivery service
    OUT_FOR_DELIVERY, // Order is out for delivery
    DELIVERED,      // Order has been delivered to the customer
    CANCELLED,      // Order was cancelled
    RETURNED;       // Order was returned by the customer
}
