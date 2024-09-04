package com.example.finalproject.domain.user.entity;

import com.example.finalproject.domain.user.dto.Register;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Getter
@NoArgsConstructor
@Table(name = "user_influencer")
@Entity
public class Influencer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @OneToOne
    @JoinColumn(name = "user_seq")
    private User user;

    private String nickname;
    private String gender;
    private LocalDate birthdate;
    private String blogLink;
    private String instagramLink;
    private String youtubeLink;
    private String tiktokLink;
    private String otherLink;

    public void influencerEntity(User user, Register register) {
        this.user = user;
        this.nickname = register.getNickname();

        this.gender = register.getGender() != null ? String.valueOf(register.getGender()) : null;
        this.birthdate = register.getBirthdate() != null ? LocalDate.parse(register.getBirthdate()) : null;
        this.blogLink = register.getBlog() != null ? register.getBlog() : null;
        this.instagramLink = register.getInstagram() != null ? register.getInstagram() : null;
        this.youtubeLink = register.getYoutube() != null ? register.getYoutube() : null;
        this.tiktokLink = register.getTiktok() != null ? register.getTiktok() : null;
        this.otherLink = register.getOther() != null ? register.getOther() : null;
    }
}
