package com.example.finalproject.domain.alarm.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "alarm")
@Entity
public class Alarm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer seq;

    @Column(name = "target_seq")
    private Integer targetSeq;

    @Column(name = "target_type")
    private Integer targetType;

    @Column(columnDefinition = "TEXT")
    private String message;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "`check`")
    private Integer check;
}
