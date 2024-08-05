package com.example.finalproject.domain.campaign.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "campaign_review")
@Entity
public class CampaignReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer seq;

    @Column(length = 50)
    private String link;

    @Column(length = 50)
    private String attachments;

    private Date date;

    @Column(name = "pc_views")
    private Integer pcViews;

    @Column(name = "mobile_views")
    private Integer mobileViews;

    @Column(length = 50)
    private String title;

    @Column(length = 50)
    private String profile;

    @Column(name = "view_count")
    private Integer viewCount;

    @Column(name = "like_count")
    private Integer likeCount;

    @Column(name = "comment_count")
    private Integer commentCount;
}
