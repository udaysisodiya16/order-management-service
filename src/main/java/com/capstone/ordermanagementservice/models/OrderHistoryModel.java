package com.capstone.ordermanagementservice.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class OrderHistoryModel extends BaseModel {

    private Long orderId;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private LocalDateTime timestamp;

}
