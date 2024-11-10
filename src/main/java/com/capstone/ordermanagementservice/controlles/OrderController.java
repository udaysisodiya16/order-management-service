package com.capstone.ordermanagementservice.controlles;

import com.capstone.ordermanagementservice.constants.OrderStatus;
import com.capstone.ordermanagementservice.dtos.OrderHistoryResponseDto;
import com.capstone.ordermanagementservice.dtos.OrderRequestDto;
import com.capstone.ordermanagementservice.dtos.OrderResponseDto;
import com.capstone.ordermanagementservice.mappers.OrderMapper;
import com.capstone.ordermanagementservice.models.OrderHistoryModel;
import com.capstone.ordermanagementservice.models.OrderModel;
import com.capstone.ordermanagementservice.services.IOrderService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private IOrderService orderService;

    private final OrderMapper orderMapper = Mappers.getMapper(OrderMapper.class);

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto> getOrderDetail(@PathVariable Long orderId) {
        OrderModel order = orderService.getOrderDetail(orderId);
        return ResponseEntity.ok(getOrderResponseDto(order));
    }

    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(@RequestBody @Valid OrderRequestDto orderRequest) {
        OrderModel order = orderMapper.orderRequestDtoToOrderModel(orderRequest);
        OrderModel createdOrder = orderService.createOrder(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(getOrderResponseDto(createdOrder));
    }

    @GetMapping("/{orderId}/status")
    public ResponseEntity<OrderStatus> getOrderStatus(@PathVariable Long orderId) {
        OrderStatus orderStatus = orderService.getOrderStatus(orderId);
        return ResponseEntity.ok(orderStatus);
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<Boolean> updateOrderStatus(@PathVariable Long orderId, @RequestParam @NotBlank OrderStatus orderStatus) {
        Boolean status = orderService.updateOrderStatus(orderId, orderStatus);
        return ResponseEntity.ok(status);
    }

    @GetMapping("/{userId}/history")
    public ResponseEntity<List<OrderResponseDto>> getAllUserOrder(@PathVariable Long userId) {
        List<OrderModel> orders = orderService.getAllUserOrder(userId);
        List<OrderResponseDto> response = orders.stream()
                .map(this::getOrderResponseDto)
                .toList();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{orderId}/track")
    public ResponseEntity<List<OrderHistoryResponseDto>> trackOrder(@PathVariable Long orderId) {
        List<OrderHistoryModel> orderHistory = orderService.trackOrder(orderId);
        return ResponseEntity.ok(orderMapper.orderHistoryModelsToOrderHistoryResponseDtos(orderHistory));
    }

    private OrderResponseDto getOrderResponseDto(OrderModel order) {
        OrderResponseDto orderResponseDto = orderMapper.orderModelToOrderResponseDto(order);
        orderResponseDto.setTotalAmount(order.getItems().stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity()).sum());
        return orderResponseDto;
    }
}

