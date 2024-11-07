package com.capstone.ordermanagementservice.services;

import com.capstone.ordermanagementservice.constants.OrderStatus;
import com.capstone.ordermanagementservice.models.OrderModel;

public interface IOrderService {

    OrderModel createOrder(OrderModel order);

    Boolean updateOrderStatus(Long orderId, OrderStatus orderStatus);

}
