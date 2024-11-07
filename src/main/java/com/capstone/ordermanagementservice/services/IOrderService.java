package com.capstone.ordermanagementservice.services;

import com.capstone.ordermanagementservice.constants.OrderStatus;
import com.capstone.ordermanagementservice.models.OrderHistoryModel;
import com.capstone.ordermanagementservice.models.OrderModel;

import java.util.List;

public interface IOrderService {

    OrderModel getOrderDetail(Long orderId);

    OrderModel createOrder(OrderModel order);

    OrderStatus getOrderStatus(Long orderId);

    Boolean updateOrderStatus(Long orderId, OrderStatus orderStatus);

    List<OrderModel> getAllUserOrder(Long userId);

    List<OrderHistoryModel> trackOrder(Long orderId);
}
