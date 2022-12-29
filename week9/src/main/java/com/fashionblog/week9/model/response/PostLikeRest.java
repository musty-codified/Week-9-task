package com.fashionblog.week9.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostLikeRest {
    private Long id;
    private boolean liked;
    private Long postId;
    private String userId;
}
