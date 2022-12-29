package com.fashionblog.week9.repository;

import com.fashionblog.week9.entity.CommentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    Optional<CommentEntity> findCommentByPostId(String userId);
    Page<CommentEntity> findAllByPostId(Long postId, Pageable pageable);
    List<CommentEntity> findAllByPostId(Long postId);
}
