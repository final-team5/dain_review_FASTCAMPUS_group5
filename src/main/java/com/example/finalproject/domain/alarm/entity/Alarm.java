package com.example.finalproject.domain.alarm.entity;

import com.example.finalproject.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seq")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

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
