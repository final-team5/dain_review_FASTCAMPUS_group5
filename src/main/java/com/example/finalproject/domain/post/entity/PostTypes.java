package com.example.finalproject.domain.post.entity;

import com.example.finalproject.domain.post.entity.enums.PostType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "post_types")
@Entity
public class PostTypes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer seq;

    @Enumerated(value = EnumType.STRING)
    @Column(length = 50)
    private PostType type;
}
