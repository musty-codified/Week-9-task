package com.fashionblog.week9.model.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CommentRest {
    private Long id;
    private String message;
    private Long postId;
    private String userId;
    private String name;
    private List<CommentLikeRest> commentLikeRests;
}
