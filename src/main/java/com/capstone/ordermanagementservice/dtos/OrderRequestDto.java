package com.capstone.ordermanagementservice.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderRequestDto {

    private Long userId;

    private List<OrderItemDto> items;

    private String deliveryAddress;

}
