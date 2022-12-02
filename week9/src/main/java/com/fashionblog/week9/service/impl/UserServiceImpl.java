package com.fashionblog.week9.service.impl;

import com.fashionblog.week9.dto.UserDto;
import com.fashionblog.week9.entity.UserEntity;
import com.fashionblog.week9.repository.UserRepository;
import com.fashionblog.week9.service.UserService;
import com.fashionblog.week9.utils.Utils;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
   private final UserRepository userRepository;

   private final ModelMapper modelMapper;

    private final Utils utils;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public UserDto createUser(UserDto userDto) {
        if(userRepository.findByEmail(userDto.getEmail())!=null) throw new RuntimeException("Records already exists");
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

    @Override
    public UserDto getUserByUserId(String UserId) {
        UserDto returnValue = new UserDto();
      UserEntity userEntity =  userRepository.findByUserId(UserId);
      if(userEntity == null) throw new UsernameNotFoundException(UserId);
      modelMapper.map(userEntity, returnValue);

        return returnValue;
    }

    @Override
    public UserDto getUser(String email) {
      UserEntity userEntity =  userRepository.findByEmail(email);
      UserDto returnValue = new UserDto();
      modelMapper.map(userEntity, returnValue);
      if(userEntity == null) throw new UsernameNotFoundException(email);

        return returnValue;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity=  userRepository.findByEmail(email);
        if(userEntity == null) throw new  UsernameNotFoundException(email);
        return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());

    }
}
