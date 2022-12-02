package com.fashionblog.week9.controller;

import com.fashionblog.week9.dto.UserDto;
import com.fashionblog.week9.model.request.UserDetailsRequestModel;
import com.fashionblog.week9.model.response.UserRest;
import com.fashionblog.week9.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")   //http:localhost:8080/users
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    private final ModelMapper modelMapper;
    @RequestMapping(method = RequestMethod.POST)
    public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails){
        UserRest returnValue = new UserRest();
        UserDto userDto = new UserDto();
        modelMapper.map(userDetails, userDto);
      UserDto createdUser =  userService.createUser(userDto);
      modelMapper.map(createdUser, returnValue);
        return returnValue ;
    }
    @RequestMapping("/{id}")
    public UserRest getUser(@PathVariable String id){

          UserRest returnValue= new UserRest();
          UserDto userDto = userService.getUserByUserId(id);
          modelMapper.map(userDto, returnValue);
        return returnValue;
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
