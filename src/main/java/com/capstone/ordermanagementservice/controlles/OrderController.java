package com.capstone.ordermanagementservice.controlles;

import com.capstone.ordermanagementservice.constants.OrderStatus;
import com.capstone.ordermanagementservice.dtos.OrderRequestDto;
import com.capstone.ordermanagementservice.dtos.OrderResponseDto;
import com.capstone.ordermanagementservice.mappers.OrderMapper;
import com.capstone.ordermanagementservice.models.OrderModel;
import com.capstone.ordermanagementservice.services.IOrderService;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private IOrderService orderService;

    private final OrderMapper orderMapper = Mappers.getMapper(OrderMapper.class);

    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(@RequestBody OrderRequestDto orderRequest) {
        OrderModel order = orderMapper.orderRequestDtoToOrderModel(orderRequest);
        OrderModel createdOrder = orderService.createOrder(order);
        OrderResponseDto orderResponseDto = orderMapper.orderModelToOrderResponseDto(createdOrder);
        orderResponseDto.setTotalAmount(createdOrder.getItems().stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity()).sum());
        return ResponseEntity.ok(orderResponseDto);
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<Boolean> updateOrderStatus(@PathVariable Long orderId, @RequestParam OrderStatus orderStatus) {
        Boolean status = orderService.updateOrderStatus(orderId, orderStatus);
        return ResponseEntity.ok(status);
    }
}

