package com.example.finalproject.domain.user.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@Builder
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer seq;

    @Column(length = 50)
    private String email;

    @Column(length = 100)
    private String id;

    @Column(length = 200)
    private String pw;

    @Column(length = 50)
    private String role;

    @Column(length = 50)
    private String name;

    @Column(length = 50)
    private String phone;

    @Column(length = 50)
    private Integer loginType;

    @Column(length = 50)
    private String profile;

    public void setProfile(String profile) {
        this.profile = profile;
    }

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "sign_up_source", length = 50)
    private String signupSource;

    @Column(name = "postal_code")
    private Integer postalCode;

    @Column(length = 50)
    private String address;

    @Column(name = "address_detail", length = 50)
    private String addressDetail;

    private Integer point;

    private Integer status;

    private Integer cancel;

    private Integer penalty;

    private Integer type;
}
