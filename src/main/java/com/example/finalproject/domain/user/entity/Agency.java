package com.example.finalproject.domain.user.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_agency")
@Entity
public class Agency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer seq;

    @Column(columnDefinition = "TEXT")
    private String reason;

    private Integer status;

    @Column(name = "create_date")
    private Date createDate;
}
