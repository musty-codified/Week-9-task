package com.fashionblog.week9.controller;

import com.fashionblog.week9.dto.CommentDto;
import com.fashionblog.week9.model.request.UserCommentRequestModel;
import com.fashionblog.week9.model.response.ApiResponse;
import com.fashionblog.week9.model.response.CommentRest;
import com.fashionblog.week9.service.PostLikeService;
import com.fashionblog.week9.service.PostService;
import com.fashionblog.week9.service.UserService;
import com.fashionblog.week9.service.impl.CommentServiceImpl;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class CommentController {
    private final CommentServiceImpl commentServiceImpl;
    private final PostService postService;
    private final UserService userService;
    private final PostLikeService postLikeService;
    private final ModelMapper modelMapper;

    @PostMapping(path = "/{userId}/post/{postId}/comment", consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ApiResponse<CommentRest> createComment(@PathVariable String userId, @PathVariable Long postId, @RequestBody UserCommentRequestModel comment) {
        CommentDto commentDto = new CommentDto();
        BeanUtils.copyProperties(comment, commentDto);
        return commentServiceImpl.comment(commentDto, userId, postId);
    }

    @PutMapping(path = "/{userId}/post/{postId}/comment/{commentId}", consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ApiResponse<CommentRest> updateComment(@PathVariable String userId, @PathVariable Long postId,
                                                  @PathVariable Long commentId, @RequestBody UserCommentRequestModel comment) {
        CommentDto commentDto = new CommentDto();
        BeanUtils.copyProperties(comment, commentDto);
        return commentServiceImpl.updateComment(commentDto, userId, postId, commentId);
    }

    @GetMapping(path = "/{userId}/post/{postId}/comment/{commentId}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ApiResponse getCommentById(@PathVariable String userId, @PathVariable Long postId,
                                      @PathVariable Long commentId) {
        return commentServiceImpl.getCommentById(userId, postId, commentId);
    }

    @GetMapping(path = "/{userId}/post/{postId}/comment",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ApiResponse<List<CommentRest>> getComments(@PathVariable String userId, @PathVariable Long postId,
                                                      @RequestParam(value = "cPage", defaultValue = "0") int cPage,
                                                      @RequestParam(value = "cLimit", defaultValue = "5") int cLimit) {
        return commentServiceImpl.getComments(userId, postId, cPage, cLimit);
    }

    @DeleteMapping(path = "/{userId}/post/{postId}/comment/{commentId}/delete",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ApiResponse deleteComment(@PathVariable String userId, @PathVariable Long postId,
                                     @PathVariable Long commentId) {
        return commentServiceImpl.deleteComment(userId, postId, commentId);
    }

}
