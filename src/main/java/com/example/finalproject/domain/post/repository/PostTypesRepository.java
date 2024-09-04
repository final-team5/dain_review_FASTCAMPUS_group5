package com.example.finalproject.domain.post.repository;

import com.example.finalproject.domain.post.entity.PostTypes;
import com.example.finalproject.domain.post.entity.enums.PostType;
import com.example.finalproject.global.exception.error.ValidErrorCode;
import com.example.finalproject.global.exception.type.ValidException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostTypesRepository extends JpaRepository<PostTypes, Integer> {

    Optional<PostTypes> findByType(PostType type);

    default PostTypes getPostTypesByTypeOrException(PostType category) {
        return findByType(category).orElseThrow(
                () -> new ValidException(ValidErrorCode.POST_TYPE_NOT_FOUND)
        );
    }
}
