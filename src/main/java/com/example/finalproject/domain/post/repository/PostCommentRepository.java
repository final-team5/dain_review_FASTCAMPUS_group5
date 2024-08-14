package com.example.finalproject.domain.post.repository;

import com.example.finalproject.domain.post.entity.PostComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostCommentRepository extends JpaRepository<PostComment, Integer> {

    Page<PostComment> findAllByPostSeq(Integer postSeq, Pageable pageable);
}
