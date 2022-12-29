package com.fashionblog.week9.service.impl;

import com.fashionblog.week9.CommentShare;
import com.fashionblog.week9.dto.CommentDto;
import com.fashionblog.week9.entity.CommentEntity;
import com.fashionblog.week9.exception.ErrorMessages;
import com.fashionblog.week9.model.response.*;
import com.fashionblog.week9.repository.CommentRepository;
import com.fashionblog.week9.repository.PostRepository;
import com.fashionblog.week9.repository.UserRepository;
import com.fashionblog.week9.service.CommentService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;
@AllArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ResponseManager<CommentRest> responseManager;
    private final ModelMapper modelMapper;


    @Override
    public ApiResponse<CommentRest> comment(CommentDto commentDto, String userId, Long postId) {
        if (userRepository.findByUserId(userId).isEmpty())
            return responseManager.error(HttpStatus.BAD_REQUEST, ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        if (!postRepository.existsById(postId))
            return responseManager.error(HttpStatus.BAD_REQUEST, ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        CommentEntity commentEntity = modelMapper.map(commentDto, CommentEntity.class);
        commentEntity.setPostId(postId);
        commentEntity.setUserId(userId);
        commentEntity.setPosts(postRepository.findById(postId).get());
        CommentEntity createdComment = commentRepository.save(commentEntity);

        CommentDto comDto = modelMapper.map(createdComment, CommentDto.class);
        CommentRest commentRest = modelMapper.map(comDto, CommentRest.class);

        return responseManager.success(HttpStatus.CREATED, commentRest);
    }

    @Override
    public ApiResponse<CommentRest> updateComment(CommentDto commentDto, String userId, Long postId, Long commentId) {
        CommentEntity commentEntity  = getComment(commentId);
        if (commentEntity == null)
            return responseManager.error(HttpStatus.BAD_REQUEST, ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        if (userRepository.findByUserId(userId).isEmpty())
            return responseManager.error(HttpStatus.BAD_REQUEST, ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        if (!postRepository.existsById(postId))
            return responseManager.error(HttpStatus.BAD_REQUEST, ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        CommentEntity comEntity = modelMapper.map(commentDto, CommentEntity.class);
        comEntity.setId(commentEntity.getId());
        comEntity.setPosts(commentEntity.getPosts());

        CommentEntity updatedComment = commentRepository.save(comEntity);
        CommentDto comDto = modelMapper.map(updatedComment, CommentDto.class);
        CommentRest commentRest = modelMapper.map(comDto, CommentRest.class);

        return responseManager.success(HttpStatus.OK, commentRest);

    }

    @Override
    public ApiResponse getCommentById(String userId, Long postId, Long commentId) {
        var validation = performValidation(responseManager, userId, postId);
        if (validation != null)
            return validation;

        CommentEntity commentEntity  = getComment(commentId);
        if (commentEntity == null)
            return responseManager.error(HttpStatus.BAD_REQUEST, ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        CommentDto comDto = modelMapper.map(commentEntity, CommentDto.class);
        CommentRest commentRest = modelMapper.map(comDto, CommentRest.class);

        return responseManager.success(HttpStatus.OK, commentRest);

    }

    private CommentEntity getComment(Long commentId) {
        return commentRepository.findById(commentId).orElse(null);
    }

    @Override
    public ApiResponse<List<CommentRest>> getComments(String userId, Long postId, int cPage, int cLimit) {
        ResponseManager<List<CommentRest>> manager = new ResponseManager<>();

        if (userRepository.findByUserId(userId).isEmpty())
            return manager.error(HttpStatus.BAD_REQUEST, ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        if (!postRepository.existsById(postId))
            return manager.error(HttpStatus.BAD_REQUEST, ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        List<CommentDto> commentDtoList = CommentShare.getCommentDto(cPage, cLimit, postId, modelMapper, commentRepository);

        Type resType = new TypeToken<List<CommentRest>>() {}.getType();
        List<CommentRest> commentRests = modelMapper.map(commentDtoList, resType);

        return manager.success(HttpStatus.OK, commentRests);

    }
    private boolean isExist(String userId) {
        return userRepository.findByUserId(userId).isEmpty();
    }

    private boolean isExist(Long postId) {
        return postRepository.existsById(postId);
    }


    @Override
    public ApiResponse deleteComment(String userId, Long postId, Long commentId) {
        ResponseManager<OperationStatus> manager = new ResponseManager<>();

        var validation = performValidation(manager, userId, postId);
        if (validation != null)
            return validation;

        CommentEntity commentEntity = getComment(commentId);
        if (commentEntity == null)
            return manager.error(HttpStatus.BAD_REQUEST, ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        commentRepository.delete(commentEntity);
        return manager.success(HttpStatus.OK, new OperationStatus(OperationName.DELETE.name(), OperationResult.SUCCESS.name()));

    }
    private ApiResponse performValidation(ResponseManager manager, String userId, Long postId) {
        if (isExist(userId))
            return manager.error(HttpStatus.BAD_REQUEST, ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        if (!isExist(postId))
            return manager.error(HttpStatus.BAD_REQUEST, ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        return null;
    }
}
