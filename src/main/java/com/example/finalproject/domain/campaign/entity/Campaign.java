package com.example.finalproject.domain.campaign.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "campaigns")
@Entity
public class Campaign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer seq;

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

    @Column(columnDefinition = "mission")
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



}
