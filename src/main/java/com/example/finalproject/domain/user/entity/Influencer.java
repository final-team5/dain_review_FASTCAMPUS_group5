package com.example.finalproject.domain.user.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;

// TODO : sns 등급 관련 속성 추가 예정
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE user_influencer SET deleted_at = NOW() WHERE seq=?")
@Where(clause = "deleted_at is NULL")
@Table(name = "user_influencer")
@Builder
@Entity
public class Influencer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer seq;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_seq")
    private User user;

    private String nickname;
    private Integer gender;
    private LocalDate birthdate;

    @Column(name = "blog_link")
    private String blogLink;

    @Column(name = "instagram_link")
    private String instagramLink;

    @Column(name = "youtube_link")
    private String youtubeLink;

    @Column(name = "tiktok_link")
    private String tiktokLink;

    @Column(name = "other_link")
    private String otherLink;

    @Column(name = "registered_at")
    private Timestamp registeredAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "deleted_at")
    private Timestamp deletedAt;

    public Influencer(User user, String nickname, Integer gender, LocalDate birthdate, String blogLink, String instagramLink, String youtubeLink, String tiktokLink, String otherLink) {
        this.user = user;
        this.nickname = nickname;
        this.gender = gender;
        this.birthdate = birthdate;
        this.blogLink = blogLink;
        this.instagramLink = instagramLink;
        this.youtubeLink = youtubeLink;
        this.tiktokLink = tiktokLink;
        this.otherLink = otherLink;
    }

    public static Influencer of(User user, String nickname, Integer gender, LocalDate birthdate, String blogLink, String instagramLink, String youtubeLink, String tiktokLink, String otherLink) {
        return new Influencer(user, nickname, gender, birthdate, blogLink, instagramLink, youtubeLink, tiktokLink, otherLink);
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
