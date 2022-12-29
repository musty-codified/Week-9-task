package com.fashionblog.week9.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity(name="posts")
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "user_id", nullable = false)
    private String userId;
    private String message;
    private String name;
    @ManyToOne
    @JoinColumn(nullable = false, name = "users_userId")
    private UserEntity users;

    @OneToMany(mappedBy = "posts", cascade = CascadeType.ALL)
    private List<PostLikeEntity> post_likes;

    @OneToMany(mappedBy = "posts", cascade = CascadeType.ALL)
    private List<CommentEntity> comments;
}
