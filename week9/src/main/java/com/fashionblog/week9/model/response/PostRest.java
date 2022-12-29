package com.fashionblog.week9.model.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PostRest {
    private Long id;
    private String userId;
    private String message;
    private String name;
    private List<PostLikeRest> postLikeRests;
    private List<CommentRest> commentRests;
}
