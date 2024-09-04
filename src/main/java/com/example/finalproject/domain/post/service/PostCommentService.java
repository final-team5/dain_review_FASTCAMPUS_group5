package com.example.finalproject.domain.post.service;

import com.example.finalproject.domain.post.dto.PostCommentDto;
import com.example.finalproject.domain.post.entity.Post;
import com.example.finalproject.domain.post.entity.PostComment;
import com.example.finalproject.domain.post.repository.PostCommentRepository;
import com.example.finalproject.domain.post.repository.PostRepository;
import com.example.finalproject.domain.user.entity.User;
import com.example.finalproject.domain.user.repository.UserRepository;
import com.example.finalproject.global.exception.error.ErrorCode;
import com.example.finalproject.global.exception.error.ValidErrorCode;
import com.example.finalproject.global.exception.type.ValidException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostCommentService {

    private final PostCommentRepository postCommentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    /**
     * 게시글에 댓글 저장
     *
     * @param userSeq : 회원 ID
     * @param postSeq : 게시글 ID
     * @param postCommentSeq : 댓글 ID (답글인 경우 사용)
     * @param comment : 게시글 댓글
     * @return PostCommentDto
     */
    @Transactional
    public PostCommentDto save(Integer userSeq, Integer postSeq, Integer postCommentSeq, String comment) {
        Post post = postRepository.getPostBySeqOrException(postSeq);

        User user = userRepository.getUserBySeqOrException(userSeq);

        PostComment postComment = PostComment.of(user, post, comment);

        saveIfIsReplyComment(postCommentSeq, post, user, postComment);

        // post comment save
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
    public PostCommentDto update(Integer userSeq, Integer postSeq, Integer postCommentSeq, String comment) {
        Post post = postRepository.getPostBySeqOrException(postSeq);

        User user = userRepository.getUserBySeqOrException(userSeq);

        PostComment postComment = postCommentRepository.getPostCommentBySeqOrException(postCommentSeq);

        validatePostCommentPostMatch(post, postComment);

        validatePostCommentUserMatch(user, postComment);

        postComment.setComment(comment);

        return PostCommentDto.from(postComment);
    }

    /**
     * 게시글에 댓글 삭제
     *
     * @param postCommentSeq : 삭제할 댓글 ID
     * @param userSeq : 로그인한 사용자 ID
     */
    @Transactional
    public void delete(Integer postCommentSeq, Integer userSeq) {
        User user = userRepository.getUserBySeqOrException(userSeq);

        PostComment postComment = postCommentRepository.getPostCommentBySeqOrException(postCommentSeq);

        validatePostCommentUserMatch(user, postComment);

        postCommentRepository.deleteById(postCommentSeq);
    }

    /**
     * 회원 본인이 작성한 댓글인지 체크
     *
     * @param user : 회원 정보
     * @param postComment : 댓글 정보
     */
    private void validatePostCommentUserMatch(User user, PostComment postComment) {
        if (!user.getSeq().equals(postComment.getUser().getSeq())) {
            throw new ValidException(ValidErrorCode.POST_COMMENT_USER_MISMATCH);
        }
    }

    /**
     * 게시글에 달린 댓글인지 체크
     *
     * @param post : 게시글 정보
     * @param postComment : 댓글 정보
     */
    private void validatePostCommentPostMatch(Post post, PostComment postComment) {
        if (!post.getSeq().equals(postComment.getPost().getSeq())) {
            throw new ValidException(ValidErrorCode.POST_COMMENT_POST_MISMATCH);
        }
    }

    /**
     * 답글 저장 기능
     *
     * @param postCommentSeq : 답글 달 댓글 ID
     * @param post : 게시판 정보
     * @param user : 사용자 정보
     * @param postComment : 댓글 정보
     */
    private void saveIfIsReplyComment(Integer postCommentSeq, Post post, User user, PostComment postComment) {
        if (postCommentSeq != null) {
            PostComment replyPostComment = postCommentRepository.getPostCommentBySeqOrException(postCommentSeq);

            validatePostCommentPostMatch(post, replyPostComment);
            validatePostCommentUserMatch(user, replyPostComment);

            postComment.setCommentSeq(replyPostComment);
            replyPostComment.getMyComments().add(postComment);
        }
    }

}
