package com.fashionblog.week9.controller;

import com.fashionblog.week9.model.response.ApiResponse;
import com.fashionblog.week9.model.response.CommentLikeRest;
import com.fashionblog.week9.service.impl.CommentLikeServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class CommentLikeController {
    private final CommentLikeServiceImpl commentLikeServiceImpl;

    @PutMapping(path = "/{userId}/post/{postId}/comment/{commentId}/comment-like",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ApiResponse<CommentLikeRest> updateCommentLike(@PathVariable Long commentId, @PathVariable Long postId,
                                                          @PathVariable String userId) {
        return commentLikeServiceImpl.updateCommentLike(commentId, postId, userId);
    }
}
