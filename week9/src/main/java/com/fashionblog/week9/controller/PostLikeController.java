package com.fashionblog.week9.controller;

import com.fashionblog.week9.model.response.ApiResponse;
import com.fashionblog.week9.model.response.PostLikeRest;
import com.fashionblog.week9.service.impl.PostLikeServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class PostLikeController {
    private final PostLikeServiceImpl postLikeServiceImpl;
    @PutMapping(path = "/{userId}/post/{postId}/post-like",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ApiResponse<PostLikeRest> updatePostLike(@PathVariable String userId, @PathVariable Long postId) {
        return postLikeServiceImpl.updatePostLike(postId, userId);
    }
}
