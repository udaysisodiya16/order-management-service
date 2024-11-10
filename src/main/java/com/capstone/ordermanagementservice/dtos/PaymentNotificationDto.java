package com.capstone.ordermanagementservice.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentNotificationDto {

    @NotBlank
    private Long orderId;

    @NotBlank
    private String paymentStatus;

    @Override
    public String toString() {
        return "PaymentNotificationDto{" +
                "orderId=" + orderId +
                ", paymentStatus='" + paymentStatus + '\'' +
                '}';
    }
}
