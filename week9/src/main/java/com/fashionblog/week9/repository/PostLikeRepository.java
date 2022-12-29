package com.fashionblog.week9.repository;

import com.fashionblog.week9.entity.CommentEntity;
import com.fashionblog.week9.entity.PostLikeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface PostLikeRepository extends JpaRepository<PostLikeEntity, Long> {
    Optional<PostLikeEntity> findPostLikeByPostId(Long postId);

    List<PostLikeEntity> findAllByPostIdAndLiked(Long postId, boolean liked);
    PostLikeEntity findAllByPostIdAndUserId(Long postId, String userId);

    List<PostLikeEntity> findAllByPostIdAndUserIdAndLiked(Long postId, String userId, boolean liked);
}
