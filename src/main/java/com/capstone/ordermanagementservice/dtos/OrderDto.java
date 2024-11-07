package com.capstone.ordermanagementservice.dtos;

import com.capstone.ordermanagementservice.models.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderDto {

    private Long userId;

    private Double totalAmount;

    private OrderStatus status;

    private List<OrderItemDto> items;

}
