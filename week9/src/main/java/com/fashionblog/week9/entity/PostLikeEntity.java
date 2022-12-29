package com.fashionblog.week9.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "post_like")
public class PostLikeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean liked;
    private Long postId;
    private String userId;
    @ManyToOne
    @JoinColumn(name = "posts_id")
    private PostEntity posts;
}
