package com.fashionblog.week9.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class CommentDto {
    private long id;
    private String message;
    private long postId;
    private String userId;
    private String name;
    private PostDto postDto;
    private List<CommentLikeDto> commentLikeDto;
}
