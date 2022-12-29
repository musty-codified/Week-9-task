package com.fashionblog.week9.model.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class OperationStatus {
    private String operationName;
    private String operationResult;
}
