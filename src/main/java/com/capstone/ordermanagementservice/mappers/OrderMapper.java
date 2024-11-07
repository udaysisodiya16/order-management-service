package com.capstone.ordermanagementservice.mappers;

import com.capstone.ordermanagementservice.dtos.OrderDto;
import com.capstone.ordermanagementservice.models.OrderModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderModel orderDtoToOrderModel(OrderDto orderRequest);

    OrderDto orderModelToOrderDto(OrderModel createdOrder);
}
