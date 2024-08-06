package com.example.finalproject.domain.post.entity;

import com.example.finalproject.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "post_comments")
@Entity
public class PostComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer seq;

    @ManyToOne
    @JoinColumn(name = "user_seq")
    private User user;

    @ManyToOne
    @JoinColumn(name = "post_seq")
    private Post post;

    @Column(columnDefinition = "TEXT")
    private String comment;

    public PostComment(User user, Post post, String comment) {
        this.user = user;
        this.post = post;
        this.comment = comment;
    }

    public static PostComment of(User user, Post post, String comment) {
        return new PostComment(user, post, comment);
    }

    public static PostComment of(Integer seq, User user, Post post, String comment) {
        return new PostComment(seq, user, post, comment);
    }
}
