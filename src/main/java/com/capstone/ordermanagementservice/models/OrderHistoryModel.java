package com.capstone.ordermanagementservice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "order_history")
public class OrderHistoryModel extends BaseModel {

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderModel order;

}
