package com.example.finalproject.domain.post.service;

import com.example.finalproject.domain.post.dto.PostCommentDto;
import com.example.finalproject.domain.post.entity.Post;
import com.example.finalproject.domain.post.entity.PostComment;
import com.example.finalproject.domain.post.repository.PostCommentRepository;
import com.example.finalproject.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostCommentService {

    private final PostCommentRepository postCommentRepository;

    /**
     * 게시글에 댓글 저장.
     *
     * @param userSeq : 회원 ID
     * @param postSeq : 게시글 ID
     * @param comment : 게시글 댓글
     * @return PostCommentDto
     */
    @Transactional
    public PostCommentDto save(Integer userSeq, Integer postSeq, String comment) {
        // TODO : Post 존재 여부 체크

        // TODO : userSeq 로 User 가져오기

        // post comment save
        PostComment postComment = PostComment.of(new User(), new Post(), comment);      // TODO : user, post 부분 채워넣기
        PostComment savedPostComment = postCommentRepository.save(postComment);

        return PostCommentDto.from(savedPostComment);
    }
}
