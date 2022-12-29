package com.fashionblog.week9.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class PostLikeDto {
    private Long id;
    private boolean liked;
    private Long postId;
    private String userId;
    private PostDto postDto;
}
