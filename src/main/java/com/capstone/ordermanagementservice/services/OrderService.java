package com.capstone.ordermanagementservice.services;

import com.capstone.ordermanagementservice.clients.KafkaClient;
import com.capstone.ordermanagementservice.constants.OrderStatus;
import com.capstone.ordermanagementservice.dtos.EmailDetailDto;
import com.capstone.ordermanagementservice.dtos.NotificationType;
import com.capstone.ordermanagementservice.dtos.OrderNotificationDto;
import com.capstone.ordermanagementservice.dtos.UserDetailResponseDto;
import com.capstone.ordermanagementservice.models.OrderHistoryModel;
import com.capstone.ordermanagementservice.models.OrderModel;
import com.capstone.ordermanagementservice.repos.OrderHistoryRepo;
import com.capstone.ordermanagementservice.repos.OrderRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
    private IUserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private KafkaClient kafkaClient;

    @Override
    public OrderModel getOrderDetail(Long orderId) {
        return orderRepo.findById(orderId).orElseThrow(() -> new IllegalArgumentException("Invalid OrderId"));
    }

    @Override
    public OrderModel createOrder(OrderModel order) {
        order.setStatus(com.capstone.ordermanagementservice.models.OrderStatus.PENDING);
        OrderModel savedOrder = orderRepo.save(order);

        // Add history
        createOrderHistory(savedOrder);

        // Publish to Kafka
        sendOrderNotification(savedOrder);

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
        sendOrderNotification(order);

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

    private void sendOrderNotification(OrderModel order) {
        UserDetailResponseDto userDetail = userService.getUserDetail(order.getUserId());
        OrderNotificationDto orderNotificationDto = new OrderNotificationDto();
        orderNotificationDto.setType(NotificationType.EMAIL);
        EmailDetailDto emailDetailDto = new EmailDetailDto();
        emailDetailDto.setFrom("ordermanagementservice@gmail.com");
        emailDetailDto.setTo(userDetail.getEmail());
        emailDetailDto.setSubject("Order Update");
        emailDetailDto.setBody("Your order with id:" + order.getId() + "has updated to " + order.getStatus());
        orderNotificationDto.setEmailDetail(emailDetailDto);
        String message;
        try {
            message = objectMapper.writeValueAsString(orderNotificationDto);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        kafkaClient.sendOrderNotification(message);
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