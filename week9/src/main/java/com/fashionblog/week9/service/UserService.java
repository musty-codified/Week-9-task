package com.fashionblog.week9.service;

import com.fashionblog.week9.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDto createUser(UserDto userDto);
    UserDto getUserByUserId(String id);
    UserDto getUser(String email);

}
