package com.example.finalproject.domain.user.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
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
@SQLDelete(sql = "UPDATE users SET deleted_at = NOW() WHERE seq=?")
@Where(clause = "deleted_at is NULL")
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
    private String profile;

    @Column(length = 50)
    private Integer loginType;

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

    @Column(name = "registered_at")
    private Timestamp registeredAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "deleted_at")
    private Timestamp deletedAt;

    public User(String email, String id, String pw, String role, String name, String phone, String profile, Integer loginType, Date createDate, String signupSource, Integer postalCode, String address, String addressDetail, Integer point, Integer status, Integer cancel, Integer penalty, Integer type) {
        this.email = email;
        this.id = id;
        this.pw = pw;
        this.role = role;
        this.name = name;
        this.phone = phone;
        this.profile = profile;
        this.loginType = loginType;
        this.createDate = createDate;
        this.signupSource = signupSource;
        this.postalCode = postalCode;
        this.address = address;
        this.addressDetail = addressDetail;
        this.point = point;
        this.status = status;
        this.cancel = cancel;
        this.penalty = penalty;
        this.type = type;
    }

    public static User of(String email, String id, String pw, String role, String name, String phone, String profile, Integer loginType, Date createDate, String signupSource, Integer postalCode, String address, String addressDetail, Integer point, Integer status, Integer cancel, Integer penalty, Integer type) {
        return new User(email, id, pw, role, name, phone, profile, loginType, createDate, signupSource, postalCode, address, addressDetail, point, status, cancel, penalty, type);
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
