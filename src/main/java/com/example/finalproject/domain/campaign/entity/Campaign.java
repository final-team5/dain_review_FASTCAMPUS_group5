package com.example.finalproject.domain.campaign.entity;

import com.example.finalproject.domain.user.entity.User;
import com.google.api.client.util.DateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE campaigns SET deleted_at = NOW() WHERE seq=?")
@Where(clause = "deleted_at is NULL")
@Table(name = "campaigns")
@Entity
public class Campaign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer seq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seq")
    private User user;

    @Column(length = 50)
    private String id;

    @Column(length = 50)
    private String category;

    @Column(length = 50)
    private String platform;

    @Column(length = 50)
    private String type;

    @Column(length = 50)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String contents;

    @Column(columnDefinition = "TEXT")
    private String service;

    @Column(name = "keyword_1", columnDefinition = "TEXT")
    private String keyword1;

    @Column(name = "keyword_2", columnDefinition = "TEXT")
    private String keyword2;

    @Column(name = "keyword_3", columnDefinition = "TEXT")
    private String keyword3;

    @Column(name = "campaign_link", columnDefinition = "TEXT")
    private String campaignLink;

    @Column(columnDefinition = "TEXT")
    private String mission;

    private Integer recruiter;

    @Column(length = 50)
    private String image;

    @Column(length = 50)
    private String city;

    @Column(length = 50)
    private String district;

    private Integer point;

    @Column(length = 50)
    private String tag;

    private Integer status;

    @Column(name = "application_start_date")
    private Date applicationStartDate;

    @Column(name = "application_end_date")
    private Date applicationEndDate;

    @Column(name = "experience_start_date")
    private Date experienceStartDate;

    @Column(name = "experience_end_date")
    private Date experienceEndDate;

    @Column(name = "experience_start_time")
    private DateTime experienceStartTime;

    @Column(name = "experience_end_time")
    private DateTime experienceEndTime;

    private Integer monday;
    private Integer tuesday;
    private Integer wednesday;
    private Integer thursday;
    private Integer friday;
    private Integer saturday;
    private Integer sunday;

    @Column(name = "hash_tag")
    private Integer hashtag;
    private Integer map;
    private Integer link;

    private Integer characters;

    @Column(name = "video_length")
    private Integer videoLength;

    @Column(name = "picture_count")
    private Integer pictureCount;

    @Column(name = "account_tag")
    private Integer accountTag;

    private Integer sound;

    @Column(name = "advertising_display")
    private Integer advertisingDisplay;

    @Column(name = "registered_at")
    private Timestamp registeredAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "deleted_at")
    private Timestamp deletedAt;

    @PrePersist
    void registeredAt() {
        this.registeredAt = Timestamp.from(Instant.now());
    }

    @PreUpdate
    void updatedAt() {
        this.updatedAt = Timestamp.from(Instant.now());
    }
}
