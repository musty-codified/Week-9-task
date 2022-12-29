package com.fashionblog.week9.service.impl;

import com.fashionblog.week9.dto.PostLikeDto;
import com.fashionblog.week9.entity.PostEntity;
import com.fashionblog.week9.entity.PostLikeEntity;
import com.fashionblog.week9.entity.UserEntity;
import com.fashionblog.week9.exception.ErrorMessages;
import com.fashionblog.week9.model.response.ApiResponse;
import com.fashionblog.week9.model.response.PostLikeRest;
import com.fashionblog.week9.model.response.ResponseManager;
import com.fashionblog.week9.repository.PostLikeRepository;
import com.fashionblog.week9.repository.PostRepository;
import com.fashionblog.week9.repository.UserRepository;
import com.fashionblog.week9.service.PostLikeService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PostLikeServiceImpl implements PostLikeService{
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    private final PostLikeRepository postLikeRepository;
    private final ResponseManager<PostLikeRest> responseManager;
    private final ModelMapper modelMapper;
    @Override
    public ApiResponse<PostLikeRest> updatePostLike(Long postId, String userId) {

        UserEntity userEntity = userRepository.findByUserId(userId).orElse(null);
        if (userEntity == null)
            return responseManager.error(HttpStatus.BAD_REQUEST, ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        PostEntity postEntity = postRepository.findById(postId).orElse(null);
        if(postEntity == null)
            return responseManager.error(HttpStatus.BAD_REQUEST, ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        PostLikeEntity postLikeEntity = postLikeRepository.findAllByPostIdAndUserId(postId, userId);
        PostLikeEntity postLike = new PostLikeEntity();
        postLike.setLiked(true);
        if (postLikeEntity != null) {
            var like = postLikeEntity.isLiked();
            postLike.setLiked(!like);
            postLike.setId(postLikeEntity.getId());
        }

        postLike.setPostId(postId);
        postLike.setUserId(userId);
        postLike.setPosts(postEntity);

        PostLikeEntity updatedPostLike = postLikeRepository.save(postLike);
        PostLikeDto postLikeDto = modelMapper.map(updatedPostLike, PostLikeDto.class);
        PostLikeRest postLikeRest = modelMapper.map(postLikeDto, PostLikeRest.class);

        return responseManager.success(HttpStatus.CREATED, postLikeRest);
    }




}
