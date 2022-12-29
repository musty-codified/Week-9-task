package com.fashionblog.week9.service.impl;

import com.fashionblog.week9.CommentShare;
import com.fashionblog.week9.dto.*;
import com.fashionblog.week9.entity.*;
import com.fashionblog.week9.exception.ErrorMessages;
import com.fashionblog.week9.model.response.*;
import com.fashionblog.week9.repository.*;
import com.fashionblog.week9.service.PostService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostLikeRepository postLikeRepository;
    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final ResponseManager<PostRest> responseManager;
    private final ModelMapper modelMapper;
    @Override
    public ApiResponse<PostRest> createPost(String userId, String post) {
        var userEntity = getUserEntity(userId);
        if(userEntity == null)
            return responseManager.error(HttpStatus.BAD_REQUEST, ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        PostEntity postEntity = new PostEntity();
        postEntity.setName(userEntity.getFirstName() + " " + userEntity.getLastName());
        postEntity.setUserId(userEntity.getUserId());
        postEntity.setMessage(post);
        postEntity.setUsers(userEntity);
        PostEntity createdPost = postRepository.save(postEntity);
        PostDto postDto = modelMapper.map(createdPost, PostDto.class);
        PostRest postRest = modelMapper.map(postDto, PostRest.class);
        return responseManager.success(HttpStatus.CREATED, postRest);
    }

    @Override
    public ApiResponse<PostRest> updatePost(String userId, Long postId, String post) {
        var userEntity = getUserEntity(userId);
        if(userEntity == null)
            return responseManager.error(HttpStatus.BAD_REQUEST, ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        var existingPost = getPostEntity(postId);
        if(existingPost == null)
            return responseManager.error(HttpStatus.BAD_REQUEST, ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        PostEntity postEntity = modelMapper.map(existingPost, PostEntity.class);
        postEntity.setMessage(post);

        PostEntity updatedPost = postRepository.save(postEntity);
        PostDto postDto = modelMapper.map(updatedPost, PostDto.class);
        PostRest postRest = modelMapper.map(postDto, PostRest.class);

        return responseManager.success(HttpStatus.CREATED, postRest);
    }

    @Override
    public ApiResponse<List<PostRest>> getPosts(String userId, int page, int limit, int cPage, int cLimit) {
        ResponseManager<List<PostRest>> manager = new ResponseManager<>();
        var userEntity = getUserEntity(userId);
        List<PostRest> postRests;

        if(userEntity == null)
            return manager.error(HttpStatus.BAD_REQUEST, ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        if (page > 0) page = page - 1;
        if (cPage > 0) cPage = cPage - 1;

        PageRequest pageable = PageRequest.of(page, limit, Sort.by("id").descending());
        Page<PostEntity> postEntities = postRepository.findAll(pageable);

        PageRequest cPageable = PageRequest.of(cPage, cLimit, Sort.by("id").descending());

        Type dtoType = new TypeToken<List<PostDto>>() {}.getType();
        List<PostDto> postDtoList = modelMapper.map(postEntities.getContent(), dtoType);

        List<PostDto> postDtos = new ArrayList<>();
        for (PostDto postDto: postDtoList) {
            var postLikeEntities = postLikeRepository.findAllByPostIdAndLiked(postDto.getId(), true);
            Type postLikeDtoType = new TypeToken<List<PostLikeDto>>() {}.getType();
            List<PostLikeDto> postLikeDtoList = modelMapper.map(postLikeEntities, postLikeDtoType);
            postDto.setPostLikeDto(postLikeDtoList);

            List<CommentDto> commentDtos = new ArrayList<>();

            Page<CommentEntity> commentEntities = commentRepository.findAllByPostId(postDto.getId(), cPageable);
            Type cDtoType = new TypeToken<List<CommentDto>>() {}.getType();
            List<CommentDto> commentDtoList = modelMapper.map(commentEntities.getContent(), cDtoType);
            for (CommentDto commentDto: commentDtoList) {
                var commentLikeEntities = commentLikeRepository.findAllByPostIdAndLiked(postDto.getId(), true);
                Type commentLikeDtoType = new TypeToken<List<CommentLikeDto>>() {}.getType();
                List<CommentLikeDto> commentLikeDtoList = modelMapper.map(commentLikeEntities, commentLikeDtoType);
                commentDto.setCommentLikeDto(commentLikeDtoList);
                commentDtos.add(commentDto);
            }
            postDto.setCommentDto(commentDtos);
            postDtos.add(postDto);
        }

        Type resType = new TypeToken<List<PostRest>>() {}.getType();
        postRests = modelMapper.map(postDtos, resType);

        for (int i = 0; i < postRests.size(); i++) {
            List<PostLikeEntity> postLikeEntities = postLikeRepository.findAllByPostIdAndLiked(postRests.get(i).getId(), true);
            Type postLikeRestType = new TypeToken<List<PostLikeRest>>() {}.getType();
            List<PostLikeRest> postLikeRestList = modelMapper.map(postLikeEntities, postLikeRestType);
            var postRest = postRests.get(i);
            postRest.setPostLikeRests(postLikeRestList);

            List<CommentEntity> commentEntities = commentRepository.findAllByPostId(postRest.getId());
            Type commentRestType = new TypeToken<List<CommentRest>>() {}.getType();
            List<CommentRest> commentRestList = modelMapper.map(commentEntities, commentRestType);

            for (int j = 0; j < commentRestList.size(); j++) {
                var commentRest = commentRestList.get(j);
                List<CommentLikeEntity> commentLikeEntities = commentLikeRepository.findAllByCommentIdAndLiked(commentRest.getId(), true);
                Type commentLikeRestType = new TypeToken<List<CommentLikeRest>>() {}.getType();
                List<CommentLikeRest> commentLikeRestList = modelMapper.map(commentLikeEntities, commentLikeRestType);
                commentRest.setCommentLikeRests(commentLikeRestList);
                commentRestList.set(j, commentRest);
            }

            postRest.setCommentRests(commentRestList);
            postRests.set(i, postRest);
        }

        return manager.success(HttpStatus.OK, postRests);
    }

    @Override
    public ApiResponse<PostRest> getPost(String userId, Long postId, int cPage, int cLimit) {

        var userEntity = getUserEntity(userId);
        if(userEntity == null)
            return responseManager.error(HttpStatus.BAD_REQUEST, ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        var existingPost = getPostEntity(postId);
        if(existingPost == null)
            return responseManager.error(HttpStatus.BAD_REQUEST, ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        var postLikeEntities = postLikeRepository.findAllByPostIdAndLiked(postId, true);
        Type postLikeDtoType = new TypeToken<List<PostLikeDto>>() {}.getType();
        List<PostLikeDto> postLikeDtoList = modelMapper.map(postLikeEntities, postLikeDtoType);

        List<CommentDto> commentDtoList = CommentShare.getCommentDto(cPage, cLimit, postId, modelMapper, commentRepository);

        CommentDto commentDto = new CommentDto();
        for (CommentDto commentDto1: commentDtoList) {
            var commentLikeEntities = commentLikeRepository.findAllByCommentIdAndLiked(commentDto.getId(), true);
            Type commentLikeDtoType = new TypeToken<List<CommentLikeDto>>() {}.getType();
            List<CommentLikeDto> commentLikeDtoList = modelMapper.map(commentLikeEntities, commentLikeDtoType);
            commentDto.setCommentLikeDto(commentLikeDtoList);
            commentDto.getCommentLikeDto();
        }

        PostDto postDto = modelMapper.map(existingPost, PostDto.class);
        postDto.setCommentDto(commentDtoList);
        postDto.setPostLikeDto(postLikeDtoList);
        postDto.setUserDto(modelMapper.map(userEntity, UserDto.class));

        Type postLikeRestType = new TypeToken<List<PostLikeRest>>() {}.getType();
        List<PostLikeRest> postLikeRestList = modelMapper.map(postLikeDtoList, postLikeRestType);

        List<CommentRest> commentRestList = CommentShare.getCommentRests(cPage, cLimit, postId, modelMapper, commentRepository);
        List<CommentRest> commentRests = new ArrayList<>();
        for (CommentRest commentRest: commentRestList) {
            var commentLikeEntities = commentLikeRepository.findAllByCommentIdAndLiked(commentRest.getId(), true);
            Type commentLikeRestType = new TypeToken<List<CommentLikeRest>>() {}.getType();
            List<CommentLikeRest> commentLikeRestList = modelMapper.map(commentLikeEntities, commentLikeRestType);
            commentRest.setCommentLikeRests(commentLikeRestList);
            commentRests.add(commentRest);
        }
        PostRest postRest = modelMapper.map(postDto, PostRest.class);
        postRest.setPostLikeRests(postLikeRestList);
        postRest.setCommentRests(commentRests);
        return responseManager.success(HttpStatus.OK, postRest);
    }
    @Override
    public ApiResponse<OperationStatus> deletePost(Long postId, String userId) {
        ResponseManager<OperationStatus> manager = new ResponseManager<>();

        UserEntity userEntity = getUserEntity(userId);
        if(userEntity == null)
            return manager.error(HttpStatus.BAD_REQUEST, ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        PostEntity postEntity = getPostEntity(postId);
        if (postEntity == null)
            return manager.error(HttpStatus.BAD_REQUEST, ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        postRepository.delete(postEntity);
        return manager.success(HttpStatus.OK, new OperationStatus(OperationName.DELETE.name(), OperationResult.SUCCESS.name()));

    }

    private UserEntity getUserEntity(String userId) {
        return userRepository.findByUserId(userId).orElse(null);
    }

    private PostEntity getPostEntity(Long postId) {
        return postRepository.findById(postId).orElse(null);
    }
}
