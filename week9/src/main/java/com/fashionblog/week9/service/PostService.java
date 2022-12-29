package com.fashionblog.week9.service;

import com.fashionblog.week9.model.response.ApiResponse;
import com.fashionblog.week9.model.response.OperationStatus;
import com.fashionblog.week9.model.response.PostRest;

import java.util.List;

public interface PostService {
    ApiResponse<PostRest> createPost(String userId, String post);
    ApiResponse<PostRest> updatePost(String userId, Long postId, String post);
    ApiResponse<List<PostRest>> getPosts(String userId, int page, int limit, int cPage, int cLimit);
    ApiResponse<PostRest> getPost(String userId, Long postId, int cPage, int cLimit);
    ApiResponse<OperationStatus> deletePost(Long postId, String userId);
}
