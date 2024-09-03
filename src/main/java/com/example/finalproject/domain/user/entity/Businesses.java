package com.example.finalproject.domain.user.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SQLDelete(sql = "UPDATE user_influencer SET deleted_at = NOW() WHERE seq=?")
@Where(clause = "deleted_at is NULL")
@Table(name = "user_enterpriser")
@Builder
@Entity
public class Businesses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer seq;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_seq")
    private User user;

    @Column(length = 50)
    private String company;

    @Column(name = "business_number", length = 50)
    private String businessNumber;

    @Column(length = 50)
    private String representative;

    @Column(name = "attached_file", length = 50)
    private String attachedFile;

    @Column(name = "registered_at")
    private Timestamp registeredAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "deleted_at")
    private Timestamp deletedAt;

    public Businesses(User user, String company, String businessNumber, String representative) {
        this.user = user;
        this.company = company;
        this.businessNumber = businessNumber;
        this.representative = representative;
    }

    public static Businesses of(User user, String company, String businessNumber, String representative) {
        return new Businesses(user, company, businessNumber, representative);
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
