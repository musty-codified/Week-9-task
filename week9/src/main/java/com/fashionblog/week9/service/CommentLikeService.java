package com.fashionblog.week9.service;

import com.fashionblog.week9.model.response.ApiResponse;
import com.fashionblog.week9.model.response.CommentLikeRest;

public interface CommentLikeService {
    ApiResponse<CommentLikeRest> updateCommentLike(Long commentId, Long postId, String userId);

}
