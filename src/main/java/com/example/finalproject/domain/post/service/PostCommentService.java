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
     * 게시글에 댓글 저장
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

    /**
     * 게시글에 댓글 수정
     *
     * @param postSeq : 게시판 ID
     * @param postCommentSeq : 수정할 댓글 ID
     * @param comment : 수정할 댓글
     * @return PostCommentDto
     */
    @Transactional
    public PostCommentDto update(Integer postSeq, Integer postCommentSeq, String comment) {
        // TODO : Post 존재 여부 체크

        // TODO : 댓글 존재 여부 체크

        // TODO : 내가 쓴 댓글인지 체크

        // TODO : 댓글 변경감지로 update

        return PostCommentDto.from(new PostComment());      // TODO : Entity 부분 수정 필요
    }

    /**
     * 게시글에 댓글 삭제
     *
     * @param postCommentSeq : 삭제할 댓글 ID
     * @param userSeq : 로그인한 사용자 ID
     */
    @Transactional
    public void delete(Integer postCommentSeq, Integer userSeq) {
        // TODO : 내가 쓴 댓글인지 체크

        postCommentRepository.deleteById(postCommentSeq);
    }
}
