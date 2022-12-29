package com.fashionblog.week9.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
@Setter
public class ApiResponse<T> {
    private int httpStatusCode;
    private HttpStatus status;
    private String message;
    private boolean success;
    private T data;
}
