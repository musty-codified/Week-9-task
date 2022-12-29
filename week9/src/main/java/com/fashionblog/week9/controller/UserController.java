package com.fashionblog.week9.controller;

import com.fashionblog.week9.dto.UserDto;
import com.fashionblog.week9.entity.UserEntity;
import com.fashionblog.week9.model.request.UserDetailsRequestModel;
import com.fashionblog.week9.model.response.ApiResponse;
import com.fashionblog.week9.model.response.OperationStatus;
import com.fashionblog.week9.model.response.UserRest;
import com.fashionblog.week9.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")   //http://localhost:3000/users root path
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    private final ModelMapper modelMapper;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE} )
    public ApiResponse<UserRest> createUser(@RequestBody UserDetailsRequestModel userDetails){
        UserDto userDto = modelMapper.map(userDetails, UserDto.class);
         return userService.createUser(userDto);
    }

//localhost:3000/users/login?email=email.com&password=password
    @PostMapping(path = "/login", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ApiResponse<UserRest> login(@RequestParam("email") String email, @RequestParam("password") String password) {
        return userService.login(email, password);

}
    @GetMapping(path = "/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ApiResponse<UserRest> getUserByUserId(@PathVariable String userId) {
        return userService.getUserByUserId(userId);
    }
    @PutMapping(path = "/{userId}", consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ApiResponse<UserRest> update(@PathVariable String userId, @RequestBody UserDto user) {
        UserDto userDto = modelMapper.map(user, UserDto.class);
        return userService.updateUser(userId, userDto);
    }
    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ApiResponse<List<UserRest>> getUsers(@RequestParam(value = "page", defaultValue = "0") int page,
                                                @RequestParam(value = "limit", defaultValue = "5") int limit) {
        return userService.getUsers(page, limit);
    }
    @DeleteMapping(path = "{userId}/delete", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ApiResponse<OperationStatus> deleteUser(@PathVariable String userId) {
        return userService.deleteUser(userId);

    }
}
