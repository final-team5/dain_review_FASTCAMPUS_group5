package com.example.finalproject.domain.user.entity;

import com.example.finalproject.domain.user.dto.Register;
import java.time.LocalDate;
import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "user_influencer")
public class Influencer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @OneToOne
    @JoinColumn(name = "user_seq")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    private String nickname;
    private String gender;
    private LocalDate birthdate;
    private String blogLink;
    private Integer blogVisitors; // 블로그 방문자 수
    private String instagramLink;
    private Integer instagramFollower; // 인스타그램 팔로워 수
    private String youtubeLink;
    private Integer youtubeSubscriber; // 유튜브 구독자 수
    private String tiktokLink;
    private Integer tiktokFollower; // 틱톡 팔로워 수
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