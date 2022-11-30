package com.fashionblog.week9.controller;

import com.fashionblog.week9.dto.UserDto;
import com.fashionblog.week9.model.request.UserDetailsRequestModel;
import com.fashionblog.week9.model.response.UserRest;
import com.fashionblog.week9.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")   //http:localhost:8080/users
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;
    @RequestMapping(method = RequestMethod.POST)
    public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails){
        UserRest returnValue = new UserRest();
        UserDto userDto = new UserDto();
        modelMapper.map(userDetails, userDto);
      UserDto createdUser =  userService.createUser(userDto);
      modelMapper.map(createdUser, returnValue);
        return returnValue ;
    }
    @RequestMapping()
    public String getUser(){
        return "getUser";
    }
    @RequestMapping(method = RequestMethod.PUT)
    public String updateUser(){
        return "updateUser";
    }
    @RequestMapping(method = RequestMethod.DELETE)
    public String deleteUser(){
        return "deleteUser";
    }

}
