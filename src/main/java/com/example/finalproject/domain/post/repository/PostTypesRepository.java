package com.example.finalproject.domain.post.repository;

import com.example.finalproject.domain.post.entity.PostTypes;
import com.example.finalproject.domain.post.entity.enums.PostType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostTypesRepository extends JpaRepository<PostTypes, Integer> {

    Optional<PostTypes> findByType(PostType type);
}
