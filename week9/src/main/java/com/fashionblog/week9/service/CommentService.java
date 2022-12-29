package com.fashionblog.week9.service;

import com.fashionblog.week9.dto.CommentDto;
import com.fashionblog.week9.model.response.ApiResponse;
import com.fashionblog.week9.model.response.CommentRest;

import java.util.List;

public interface CommentService {
    ApiResponse<CommentRest> comment(CommentDto commentDto, String userId, Long postId);
    ApiResponse<CommentRest> updateComment(CommentDto commentDto, String userId, Long postId, Long commentId);
    ApiResponse getCommentById(String userId, Long postId, Long commentId);

    ApiResponse<List<CommentRest>> getComments(String userId, Long postId, int cPage, int cLimit);

    ApiResponse deleteComment(String userId, Long postId, Long commentId);
}
