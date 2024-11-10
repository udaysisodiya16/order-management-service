package com.capstone.ordermanagementservice.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderRequestDto {

    @NotBlank
    private Long userId;

    @NotBlank
    private List<OrderItemDto> items;

    @NotBlank
    private String deliveryAddress;

}
