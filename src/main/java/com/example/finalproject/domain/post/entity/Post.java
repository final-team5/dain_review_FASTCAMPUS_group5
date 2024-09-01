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

@Getter
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE posts SET deleted_at = NOW() WHERE seq=?")
@Where(clause = "deleted_at is NULL")
@Table(name = "posts")
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer seq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seq")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_seq")
    @Setter
    private PostCategories postCategories;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_seq")
    private PostTypes postTypes;

    @Setter
    @Column(length = 50)
    private String title;

    @Setter
    @Column(columnDefinition = "TEXT")
    private String contents;

    @Column(name = "view_count")
    private Integer viewCount;

    @Column(name = "registered_at")
    private Timestamp registeredAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "deleted_at")
    private Timestamp deletedAt;

    public Post(User user, PostCategories postCategories, PostTypes postTypes, String title, String contents, Integer viewCount) {
        this.user = user;
        this.postCategories = postCategories;
        this.postTypes = postTypes;
        this.title = title;
        this.contents = contents;
        this.viewCount = viewCount;
    }

    public static Post of(User user, PostCategories postCategories, PostTypes postTypes, String title, String contents, Integer viewCount) {
        return new Post(user, postCategories, postTypes, title, contents, viewCount);
    }

    @PrePersist
    void registeredAt() {
        this.registeredAt = Timestamp.from(Instant.now());
    }

    @PreUpdate
    void updatedAt() {
        this.updatedAt = Timestamp.from(Instant.now());
    }

    public boolean isNotWriter(User user) {
        return !this.isWriter(user);
    }

    public boolean isWriter(User user) {
        return this.user.getSeq().equals(user.getSeq());
    }
}
