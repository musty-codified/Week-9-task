package com.fashionblog.week9.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private Long id;
    private String userId;
    private String message;
    private UserDto userDto;
    private List<PostLikeDto> postLikeDto;
    private List<CommentDto> commentDto;
}
