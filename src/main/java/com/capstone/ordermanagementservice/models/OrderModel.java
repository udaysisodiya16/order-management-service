package com.capstone.ordermanagementservice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "orders")
public class OrderModel extends BaseModel {

    private Long userId;

    private String deliveryAddress;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItemModel> items;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderHistoryModel> history;

}

