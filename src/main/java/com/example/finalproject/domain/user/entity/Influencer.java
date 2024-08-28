package com.example.finalproject.domain.user.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_influencer")
@Builder
@Entity
public class Influencer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer seq;

    @OneToOne
    @JoinColumn(name = "user_seq")
    private User user;

    private String nickname;
    private String gender;
    private LocalDate birthdate;
    private String blogLink;

    @Column(name = "blog_rank", length = 50)
    private String blogRank;

    @Column(name = "blog_visitors")
    private Integer blogVisitors;

    @Column(name = "instagram_link", columnDefinition = "TEXT")
    private String instagramLink;

    @Column(name = "instagram_rank", length = 50)
    private String instagramRank;

    @Column(name = "instagram_follower")
    private Integer instagramFollower;

    @Column(name = "youtube_link", columnDefinition = "TEXT")
    private String youtubeLink;

    @Column(name = "youtube_rank", length = 50)
    private String youtubeRank;

    @Column(name = "youtube_subscriber")
    private Integer youtubeSubscriber;

    @Column(name = "tiktok_link", columnDefinition = "TEXT")
    private String tiktokLink;

    @Column(name = "tiktok_rank", length = 50)
    private String tiktokRank;

    @Column(name = "tiktok_follower")
    private Integer tiktokFollower;

    @Column(name = "other_link", columnDefinition = "TEXT")
    private String otherLink;

    @Column(name = "other_rank", length = 50)
    private String otherRank;
}
