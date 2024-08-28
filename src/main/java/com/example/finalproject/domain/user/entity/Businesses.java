package com.example.finalproject.domain.user.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_enterpriser")
@Builder
@Entity
public class Businesses {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer seq;

    @OneToOne
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
}
