package com.fashionblog.week9.repository;

import com.fashionblog.week9.entity.CommentLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLikeEntity, Long> {
    Optional<CommentLikeEntity> findCommentLikeByCommentId(Long commentId);
    List<CommentLikeEntity> findAllByPostIdAndLiked(Long postId, boolean liked);
    List<CommentLikeEntity> findAllByCommentIdAndLiked(Long commentId, boolean liked);

    List<CommentLikeEntity> findAllByPostId(Long postId);
    CommentLikeEntity findCommentLikeByCommentIdAndPostIdAndUserId(Long commentId, Long postId, String userId);
}
