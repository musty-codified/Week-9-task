package com.fashionblog.week9.repository;

import com.fashionblog.week9.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Long> {
    PostEntity findByUserId(String userId);


}
