package com.example.finalproject.domain.user.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_enterpriser")
@Entity
public class Enterpriser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer seq;

    @Column(length = 50)
    private String company;

    @Column(name = "business_number", length = 50)
    private String businessNumber;

    @Column(length = 50)
    private String representative;

    @Column(name = "attached_file", length = 50)
    private String attachedFile;
}
