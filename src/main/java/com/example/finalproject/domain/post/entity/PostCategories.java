package com.example.finalproject.domain.post.entity;

import com.example.finalproject.domain.post.entity.enums.PostCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "posts_categories")
@Entity
public class PostCategories {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer seq;

    @Enumerated(value = EnumType.STRING)
    @Column(length = 50)
    private PostCategory category;
}
