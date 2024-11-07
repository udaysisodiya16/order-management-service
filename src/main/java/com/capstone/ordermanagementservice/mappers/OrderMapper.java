package com.capstone.ordermanagementservice.mappers;

import com.capstone.ordermanagementservice.dtos.OrderHistoryResponseDto;
import com.capstone.ordermanagementservice.dtos.OrderRequestDto;
import com.capstone.ordermanagementservice.dtos.OrderResponseDto;
import com.capstone.ordermanagementservice.models.OrderHistoryModel;
import com.capstone.ordermanagementservice.models.OrderModel;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderModel orderRequestDtoToOrderModel(OrderRequestDto orderRequest);

    OrderResponseDto orderModelToOrderResponseDto(OrderModel createdOrder);

    List<OrderHistoryResponseDto> orderHistoryModelsToOrderHistoryResponseDtos(List<OrderHistoryModel> orderHistory);
}
