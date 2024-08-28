package com.example.finalproject.domain.post.entity;

import com.example.finalproject.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE ${schema}.post_comments SET deleted_at = NOW() where seq=?")
@Where(clause = "deleted_at is NULL")
@Table(name = "post_comments")
@Entity
public class PostComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer seq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seq")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_seq")
    private Post post;

    @Setter
    @Column(columnDefinition = "TEXT")
    private String comment;

    @Setter
    @ManyToOne
    @JoinColumn(name = "comment_seq")
    private PostComment commentSeq;

    @OneToMany(mappedBy = "commentSeq", orphanRemoval = true)
    private List<PostComment> myComments = new ArrayList<>();

    @Column(name = "registered_at")
    private Timestamp registeredAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "deleted_at")
    private Timestamp deletedAt;

    public PostComment(User user, Post post, String comment) {
        this.user = user;
        this.post = post;
        this.comment = comment;
    }

    public PostComment(Integer seq, User user, Post post, String comment) {
        this.seq = seq;
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

    @PrePersist
    void registeredAt() {
        this.registeredAt = Timestamp.from(Instant.now());
    }

    @PreUpdate
    void updatedAt() {
        this.updatedAt = Timestamp.from(Instant.now());
    }
}
