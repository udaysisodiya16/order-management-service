package com.capstone.ordermanagementservice.services;

import com.capstone.ordermanagementservice.dtos.UserDetailResponseDto;

public interface IUserService {

    UserDetailResponseDto getUserDetail(Long userId);

}
