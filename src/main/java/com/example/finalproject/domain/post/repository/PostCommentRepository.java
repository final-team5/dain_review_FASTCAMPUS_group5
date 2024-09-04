package com.example.finalproject.domain.post.repository;

import com.example.finalproject.domain.post.entity.Post;
import com.example.finalproject.domain.post.entity.PostComment;
import com.example.finalproject.global.exception.error.ValidErrorCode;
import com.example.finalproject.global.exception.type.ValidException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PostCommentRepository extends JpaRepository<PostComment, Integer> {

    @Query(value = "SELECT pc FROM PostComment pc WHERE pc.commentSeq is NULL AND pc.post = :post")
    Page<PostComment> findAllByPost(Post post, Pageable pageable);

    default PostComment getPostCommentBySeqOrException(Integer postCommentSeq) {
        return findById(postCommentSeq).orElseThrow(
                () -> new ValidException(ValidErrorCode.POST_COMMENT_NOT_FOUND)
        );
    }

}
