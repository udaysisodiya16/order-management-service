package com.capstone.ordermanagementservice.dtos;

import com.capstone.ordermanagementservice.constants.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class OrderResponseDto {

    private Long orderId;

    private LocalDateTime createdAt;

    private Double totalAmount;

    private OrderStatus status;

    private List<OrderItemDto> items;

}
