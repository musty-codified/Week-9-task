package com.fashionblog.week9.service.impl;

import com.fashionblog.week9.dto.CommentLikeDto;
import com.fashionblog.week9.entity.CommentLikeEntity;
import com.fashionblog.week9.exception.ErrorMessages;
import com.fashionblog.week9.model.response.ApiResponse;
import com.fashionblog.week9.model.response.CommentLikeRest;
import com.fashionblog.week9.model.response.ResponseManager;
import com.fashionblog.week9.repository.CommentLikeRepository;
import com.fashionblog.week9.repository.CommentRepository;
import com.fashionblog.week9.repository.PostRepository;
import com.fashionblog.week9.repository.UserRepository;
import com.fashionblog.week9.service.CommentLikeService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CommentLikeServiceImpl implements CommentLikeService {
    private final CommentLikeRepository commentLikeRepository;
    private final CommentRepository commentRepository;
    private final ResponseManager<CommentLikeRest> responseManager;

    private final UserRepository userRepository;

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;
    @Override
    public ApiResponse<CommentLikeRest> updateCommentLike(Long commentId, Long postId, String userId) {
        if (userRepository.findByUserId(userId).orElse(null) == null)
            return responseManager.error(HttpStatus.BAD_REQUEST, ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        if (postRepository.findById(postId).orElse(null) == null)
            return responseManager.error(HttpStatus.BAD_REQUEST, ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        var comment = commentRepository.findById(commentId).orElse(null);
        if (comment == null)
            return responseManager.error(HttpStatus.BAD_REQUEST, ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        CommentLikeEntity commentLikeEntity = commentLikeRepository
                .findCommentLikeByCommentIdAndPostIdAndUserId(commentId, postId, userId);
        CommentLikeEntity comLike = new CommentLikeEntity();
        comLike.setLiked(true);

        if (commentLikeEntity != null) {
            var like = commentLikeEntity.isLiked();
            comLike.setLiked(!like);
            comLike.setId(commentLikeEntity.getId());
        }

        comLike.setUserId(userId);
        comLike.setPostId(postId);
        comLike.setCommentId(commentId);
        comLike.setComments(comment);

        var createdCommLike = commentLikeRepository.save(comLike);
        CommentLikeDto commentLikeDto = modelMapper.map(createdCommLike, CommentLikeDto.class);
        CommentLikeRest commentLikeRest = modelMapper.map(commentLikeDto, CommentLikeRest.class);

        return responseManager.success(HttpStatus.OK, commentLikeRest);
    }
    }

