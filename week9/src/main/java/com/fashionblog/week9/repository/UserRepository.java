package com.fashionblog.week9.repository;

import com.fashionblog.week9.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
      UserEntity findByEmail(String email);
      UserEntity findByUserId(String UserId);
}
