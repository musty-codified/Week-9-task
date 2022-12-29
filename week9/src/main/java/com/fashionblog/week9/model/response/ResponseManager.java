package com.fashionblog.week9.model.response;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ResponseManager <T>{
    public ApiResponse<T> success(HttpStatus status, T data) {
        return new ApiResponse<>(status.value(), status,"Request successful", true, data);
    }

    public ApiResponse<T> error(HttpStatus status, String errorMessage) {
        return new ApiResponse<>(status.value(), status,errorMessage, false, null);
    }
}
