package com.fashionblog.week9.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserCommentRequestModel {
    private String message;
    private long postId;
    private String userId;
    private String name;
}
