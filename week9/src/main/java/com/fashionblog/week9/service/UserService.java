package com.fashionblog.week9.service;

import com.fashionblog.week9.dto.UserDto;
import com.fashionblog.week9.entity.UserEntity;
import com.fashionblog.week9.model.response.ApiResponse;
import com.fashionblog.week9.model.response.OperationStatus;
import com.fashionblog.week9.model.response.UserRest;

import java.util.List;

public interface UserService{
    ApiResponse<UserRest> createUser(UserDto user);
    ApiResponse<UserRest> login(String email, String password);
    UserEntity getUserByEmail(String email);
    UserEntity getUserById(String id);
    ApiResponse<UserRest> getUserByUserId(String userId);
    ApiResponse<UserRest> updateUser(String userId, UserDto user);
    ApiResponse<List<UserRest>> getUsers(int page, int limit);
    ApiResponse<OperationStatus> deleteUser(String userId);

}
