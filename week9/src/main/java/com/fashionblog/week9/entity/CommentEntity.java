package com.fashionblog.week9.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity(name = "comments")
@Getter
@Setter
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;
    private Long postId;
    private String userId;
    private String name;

    @ManyToOne
    @JoinColumn(name = "posts_id")
    private PostEntity posts;
    @OneToMany(mappedBy = "comments", cascade = CascadeType.ALL)
    private List<CommentLikeEntity> comment_likes;

}
