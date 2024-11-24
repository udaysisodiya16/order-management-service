package com.capstone.ordermanagementservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderNotificationDto {

    private NotificationType type;

    private EmailDetailDto emailDetail;
}
