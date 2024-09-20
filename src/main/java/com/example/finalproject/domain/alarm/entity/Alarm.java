package com.example.finalproject.domain.alarm.entity;

import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Builder;
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

    @Builder
    public Alarm(Integer targetSeq, Integer targetType, String message) {
        this.targetSeq = targetSeq;
        this.targetType = targetType;
        this.message = message;
    }
}
