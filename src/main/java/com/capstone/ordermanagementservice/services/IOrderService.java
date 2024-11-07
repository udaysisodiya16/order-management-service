package com.capstone.ordermanagementservice.services;

import com.capstone.ordermanagementservice.models.OrderModel;
import com.capstone.ordermanagementservice.models.OrderStatus;

public interface IOrderService {

    OrderModel createOrder(OrderModel order);

    Boolean updateOrderStatus(Long orderId, OrderStatus status);

}
