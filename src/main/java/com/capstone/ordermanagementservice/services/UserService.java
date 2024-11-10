package com.capstone.ordermanagementservice.services;

import com.capstone.ordermanagementservice.dtos.UserDetailResponseDto;
import com.capstone.ordermanagementservice.utils.RestHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    @Autowired
    private RestHelper restHelper;

    @Value("${user.management.service.url}")
    private String userManagementServiceUrl;

    @Override
    public UserDetailResponseDto getUserDetail(Long userId) {
        ResponseEntity<UserDetailResponseDto> responseEntity =
                restHelper.requestForEntity(userManagementServiceUrl + "/user/{id}", HttpMethod.GET, null, UserDetailResponseDto.class, userId);
        return responseEntity.getBody();
    }
}
