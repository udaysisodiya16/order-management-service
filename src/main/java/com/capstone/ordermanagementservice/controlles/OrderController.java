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

import java.util.List;

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
        return ResponseEntity.ok(getOrderResponseDto(createdOrder));
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<Boolean> updateOrderStatus(@PathVariable Long orderId, @RequestParam OrderStatus orderStatus) {
        Boolean status = orderService.updateOrderStatus(orderId, orderStatus);
        return ResponseEntity.ok(status);
    }

    @GetMapping("/{userId}/history")
    public ResponseEntity<List<OrderResponseDto>> getOrderHistory(@PathVariable Long userId) {
        List<OrderModel> orders = orderService.getOrderHistory(userId);
        List<OrderResponseDto> response = orders.stream()
                .map(this::getOrderResponseDto)
                .toList();
        return ResponseEntity.ok(response);
    }

    private OrderResponseDto getOrderResponseDto(OrderModel order) {
        OrderResponseDto orderResponseDto = orderMapper.orderModelToOrderResponseDto(order);
        orderResponseDto.setTotalAmount(order.getItems().stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity()).sum());
        return orderResponseDto;
    }
}

