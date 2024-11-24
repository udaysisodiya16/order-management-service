package com.capstone.ordermanagementservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailDetailDto {

    private String to;

    private String from;

    private String subject;

    private String body;

}
