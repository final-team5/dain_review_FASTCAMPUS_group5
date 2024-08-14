package com.example.finalproject.domain.user.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_influencer")
@Entity
public class Influencer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer seq;

    @Column(length = 50)
    private String nickname;

    private Date birthdate;

    private Integer gender;

    @Column(name = "blog_link", columnDefinition = "TEXT")
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
