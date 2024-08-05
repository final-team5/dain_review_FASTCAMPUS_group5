package com.example.finalproject.domain.user.entity;

import lombok.AllArgsConstructor;
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
    private String id;


    private String pw;
    private String name;
    private String phone;
    private String signupSource;
    private Integer postalCode;
    private String address;
    private String addressDetail;
    private String profile;
    private Integer point;
    private Integer status;
    private Integer cancel;
    private Integer penalty;

    private String nickname;
    private Date birthdate;
    private Integer gender;
    private String blogLink;
    private String blogRank;
    private Integer blogVisitors;
    private String instagramLink;
    private String instagramRank;
    private Integer instagramFollower;
    private String youtubeLink;
    private String youtubeRank;
    private Integer youtubeSubscriber;
    private String tiktokLink;
    private String tiktokRank;
    private Integer tiktokFollower;
    private String otherLink;
    private String otherRank;
}
