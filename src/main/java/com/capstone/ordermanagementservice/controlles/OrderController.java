package com.capstone.ordermanagementservice.controlles;

import com.capstone.ordermanagementservice.constants.OrderStatus;
import com.capstone.ordermanagementservice.dtos.OrderDto;
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
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto orderRequest) {
        OrderModel order = orderMapper.orderDtoToOrderModel(orderRequest);
        OrderModel createdOrder = orderService.createOrder(order);
        return ResponseEntity.ok(orderMapper.orderModelToOrderDto(createdOrder));
    }

    @PutMapping("/{orderId}/status")
    public ResponseEntity<Boolean> updateOrderStatus(@PathVariable Long orderId, @RequestParam OrderStatus orderStatus) {
        Boolean status = orderService.updateOrderStatus(orderId, orderStatus);
        return ResponseEntity.ok(status);
    }
}

