package com.capstone.ordermanagementservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderNotificationDto {
    private String to;
    private String from;
    private String subject;
    private String body;
}
