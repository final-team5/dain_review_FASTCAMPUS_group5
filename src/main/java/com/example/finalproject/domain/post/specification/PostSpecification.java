package com.example.finalproject.domain.post.specification;

import com.example.finalproject.domain.post.entity.Post;
import com.example.finalproject.domain.post.entity.enums.PostType;
import org.springframework.data.jpa.domain.Specification;

public class PostSpecification {

    public static Specification<Post> findByPostCategory(Integer categorySeq) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.join("postCategories").get("seq"), categorySeq));
    }

    public static Specification<Post> findByPostType(PostType postType) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.join("postTypes").get("type"), postType);
    }

    public static Specification<Post> findByKeywordInUsername(String searchWord) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(root.join("user").get("name"), "%" + searchWord + "%"));
    }

    public static Specification<Post> findByKeywordInTitle(String searchWord) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), "%" + searchWord + "%"));
    }

    public static Specification<Post> findByKeywordInContents(String searchWord) {
        return ((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("contents"), "%" + searchWord + "%"));
    }

    public static Specification<Post> findByKeywordInAll(String searchWord) {
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder
                        .or(
                                criteriaBuilder.like(root.join("user").get("name"), "%" + searchWord + "%"),
                                criteriaBuilder.like(root.get("title"), "%" + searchWord + "%"),
                                criteriaBuilder.like(root.get("contents"), "%" + searchWord + "%")
                        )
        );
    }
}
