package com.capstone.ordermanagementservice.services;

import com.capstone.ordermanagementservice.constants.OrderStatus;
import com.capstone.ordermanagementservice.models.OrderHistoryModel;
import com.capstone.ordermanagementservice.models.OrderModel;
import com.capstone.ordermanagementservice.repos.OrderHistoryRepo;
import com.capstone.ordermanagementservice.repos.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class OrderService implements IOrderService {

    @Autowired
    private OrderRepo orderRepo;

    @Autowired
    private OrderHistoryRepo orderHistoryRepo;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public OrderModel getOrderDetail(Long orderId) {
        return orderRepo.findById(orderId).orElseThrow(() -> new IllegalArgumentException("Invalid OrderId"));
    }

    @Override
    public OrderModel createOrder(OrderModel order) {
        order.setStatus(com.capstone.ordermanagementservice.models.OrderStatus.CREATED);
        OrderModel savedOrder = orderRepo.save(order);

        // Add history
        createOrderHistory(savedOrder);

        // Publish to Kafka
        kafkaTemplate.send("order-topic", "OrderCreated: " + savedOrder.getId());

        return savedOrder;
    }

    @Override
    public OrderStatus getOrderStatus(Long orderId) {
        return OrderStatus.valueOf(orderRepo.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid OrderId"))
                .getStatus().name());
    }

    @Override
    public Boolean updateOrderStatus(Long orderId, OrderStatus orderStatus) {
        OrderModel order = orderRepo.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        order.setStatus(com.capstone.ordermanagementservice.models.OrderStatus.valueOf(orderStatus.name()));
        orderRepo.save(order);

        // Add history
        createOrderHistory(order);

        // Publish to Kafka
        kafkaTemplate.send("order-topic", "OrderStatusUpdated: " + order.getId() + " -> " + orderStatus);

        return true;
    }

    private void createOrderHistory(OrderModel order) {
        OrderHistoryModel history = new OrderHistoryModel();
        history.setOrderId(order.getId());
        history.setStatus(order.getStatus());
        history.setCreatedAt(order.getCreatedAt());
        history.setLastUpdatedAt(order.getLastUpdatedAt());
        history.setTimestamp(LocalDateTime.now());
        orderHistoryRepo.save(history);
    }

    @Override
    public List<OrderModel> getAllUserOrder(Long userId) {
        return orderRepo.findByUserIdOrderByCreatedAtDesc(userId);
    }

    @Override
    public List<OrderHistoryModel> trackOrder(Long orderId) {
        return orderHistoryRepo.findAllByOrderId(orderId);
    }
}