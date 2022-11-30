package com.fashionblog.week9.service.impl;

import com.fashionblog.week9.dto.UserDto;
import com.fashionblog.week9.entity.UserEntity;
import com.fashionblog.week9.repository.UserRepository;
import com.fashionblog.week9.service.UserService;
import com.fashionblog.week9.utils.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    Utils utils;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public UserDto createUser(UserDto userDto) {
        if(userRepository.findUserByEmail(userDto.getEmail())!=null) throw new RuntimeException("Records already exists");
        UserEntity userEntity = new UserEntity();
        modelMapper.map(userDto, userEntity);
        String publicUserId = utils.generateUserId(10);
        userEntity.setUserId(publicUserId);
        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));


        UserEntity savedUser = userRepository.save(userEntity);
        UserDto returnValue = new UserDto();
                modelMapper.map(savedUser, returnValue);
       return returnValue;


    }
}
