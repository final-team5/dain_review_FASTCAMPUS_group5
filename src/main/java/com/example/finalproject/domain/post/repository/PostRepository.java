package com.example.finalproject.domain.post.repository;

import com.example.finalproject.domain.post.entity.Post;
import com.example.finalproject.domain.user.entity.User;
import com.example.finalproject.global.exception.error.ValidErrorCode;
import com.example.finalproject.global.exception.type.ValidException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Integer>, JpaSpecificationExecutor<Post> {

    @Modifying
    @Query("UPDATE Post p SET p.viewCount = p.viewCount + 1 WHERE p.seq = :seq")
    void updateViewCounts(@Param("seq") Integer seq);

    @Query("SELECT p FROM Post p WHERE p.postCategories.seq = :seq")
    Page<Post> findAllByCategorySeq(
            @Param("seq") Integer seq,
            Pageable pageable
    );

    @Query("SELECT p FROM Post p WHERE p.postCategories.seq = :seq AND p.user.name LIKE %:searchKeyword%")
    Page<Post> findByUsernameContaining(
            @Param(value = "seq") Integer seq,
            @Param(value = "searchKeyword") String username,
            Pageable pageable);

    @Query("SELECT p FROM Post p WHERE p.postCategories.seq = :seq AND p.title LIKE %:searchKeyword%")
    Page<Post> findByTitleContaining(
            @Param(value = "seq") Integer seq,
            @Param(value = "searchKeyword") String title,
            Pageable pageable);

    @Query("SELECT p FROM Post p WHERE p.postCategories.seq = :seq AND p.contents LIKE %:searchKeyword%")
    Page<Post> findByContentsContaining(
            @Param(value = "seq") Integer seq,
            @Param(value = "searchKeyword") String contents,
            Pageable pageable);

    @Query("SELECT p FROM Post p WHERE p.title LIKE %:searchKeyword% OR p.user.name LIKE %:searchKeyword% OR p.contents LIKE %:searchKeyword% AND p.postCategories.seq = :seq")
    Page<Post> findByContaining(
            @Param(value = "seq") Integer seq,
            @Param(value = "searchKeyword") String searchKeyword,
            Pageable pageable
    );

    @Query("SELECT p FROM Post p JOIN Influencer i WHERE i.user = :user")
    Optional<Post> findByUserWithJoinInfluencer(@Param(value = "user") User user);

    @Query("SELECT p FROM Post p JOIN Businesses b WHERE b.user = :user")
    Optional<Post> findByUserWithJoinBusinesses(@Param(value = "user") User user);

    default Post getPostBySeqOrException(Integer postSeq) {
        return findById(postSeq).orElseThrow(
                () -> new ValidException(ValidErrorCode.POST_NOT_FOUND)
        );
    }

    default Post getPostByUserSeqWithJoinInfluencerOrException(User user) {
        return findByUserWithJoinInfluencer(user).orElseThrow(
                () -> new ValidException(ValidErrorCode.POST_NOT_FOUND)
        );
    }

    default Post getPostByUserSeqWithJoinBusinessesOrException(User user) {
        return findByUserWithJoinBusinesses(user).orElseThrow(
                () -> new ValidException(ValidErrorCode.POST_NOT_FOUND)
        );
    }
}
