package com.capstone.ordermanagementservice.consumers;

import com.capstone.ordermanagementservice.constants.OrderStatus;
import com.capstone.ordermanagementservice.dtos.PaymentNotificationDto;
import com.capstone.ordermanagementservice.services.IOrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private IOrderService orderService;

    @KafkaListener(topics = "${kafka.topic.payment.notification}", groupId = "OrderManagementService")
    public void paymentNotification(String message) {
        try {
            PaymentNotificationDto paymentNotificationDto = objectMapper.readValue(message, PaymentNotificationDto.class);
            if (paymentNotificationDto.getPaymentStatus().equals("SUCCESS")) {
                Boolean status = orderService.updateOrderStatus(paymentNotificationDto.getOrderId(), OrderStatus.CONFIRMED);
                if (Boolean.FALSE.equals(status)) {
                    throw new RuntimeException("OrderStatus not updated for " + paymentNotificationDto);
                }
            } else {
                System.out.println("Payment failed for OrderId :" + paymentNotificationDto.getOrderId());
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
}
