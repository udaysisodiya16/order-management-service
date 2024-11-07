package com.capstone.ordermanagementservice.services;

import com.capstone.ordermanagementservice.models.OrderHistoryModel;
import com.capstone.ordermanagementservice.models.OrderModel;
import com.capstone.ordermanagementservice.models.OrderStatus;
import com.capstone.ordermanagementservice.repos.OrderHistoryRepo;
import com.capstone.ordermanagementservice.repos.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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
    public OrderModel createOrder(OrderModel order) {
        order.setStatus(OrderStatus.CREATED);
        order.setCreatedAt(LocalDateTime.now());

        OrderModel savedOrder = orderRepo.save(order);

        // Add history
        createOrderHistory(savedOrder);

        // Publish to Kafka
        kafkaTemplate.send("order-topic", "OrderCreated: " + savedOrder.getId());

        return savedOrder;
    }


    @Override
    public Boolean updateOrderStatus(Long orderId, OrderStatus status) {
        OrderModel order = orderRepo.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));

        order.setStatus(status);
        orderRepo.save(order);

        // Add history
        createOrderHistory(order);

        // Publish to Kafka
        kafkaTemplate.send("order-topic", "OrderStatusUpdated: " + order.getId() + " -> " + status);

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
}