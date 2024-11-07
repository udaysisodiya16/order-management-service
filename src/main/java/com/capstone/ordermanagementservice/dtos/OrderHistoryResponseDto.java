package com.capstone.ordermanagementservice.dtos;

import com.capstone.ordermanagementservice.constants.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class OrderHistoryResponseDto {

    private Long orderId;

    private LocalDateTime createdAt;

    private Double totalAmount;

    private OrderStatus status;

    private LocalDateTime timestamp;

}
