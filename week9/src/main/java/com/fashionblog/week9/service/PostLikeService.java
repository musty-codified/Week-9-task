package com.fashionblog.week9.service;

import com.fashionblog.week9.model.response.ApiResponse;
import com.fashionblog.week9.model.response.PostLikeRest;

public interface PostLikeService {
    ApiResponse<PostLikeRest> updatePostLike(Long postId, String userId);
}
