package com.capstone.ordermanagementservice.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderRequestDto {

    @NotNull(message = "User ID cannot be null")
    private Long userId;

    @NotNull(message = "Items cannot be null")
    @Size(min = 1, message = "There must be at least one item in the order")
    private List<OrderItemDto> items;

    @NotBlank(message = "Delivery address cannot be blank")
    private String deliveryAddress;

}
