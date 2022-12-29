package com.fashionblog.week9.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity(name = "comment_like")
public class CommentLikeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean liked;
    private Long postId;
    private String userId;
    private Long commentId;
    @ManyToOne
    @JoinColumn(name = "comments_id")
    private CommentEntity comments;
}
