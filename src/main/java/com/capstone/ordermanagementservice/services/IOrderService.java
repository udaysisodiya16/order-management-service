package com.capstone.ordermanagementservice.services;

import com.capstone.ordermanagementservice.constants.OrderStatus;
import com.capstone.ordermanagementservice.models.OrderModel;

import java.util.List;

public interface IOrderService {

    OrderModel createOrder(OrderModel order);

    Boolean updateOrderStatus(Long orderId, OrderStatus orderStatus);

    List<OrderModel> getOrderHistory(Long userId);
}
