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
        Post post = getPostOrException(postSeq);

        User user = getUserOrException(userSeq);

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
        Post post = getPostOrException(postSeq);

        User user = getUserOrException(userSeq);

        PostComment postComment = getPostCommentOrException(postCommentSeq);

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
        User user = getUserOrException(userSeq);

        PostComment postComment = getPostCommentOrException(postCommentSeq);

        validatePostCommentUserMatch(user, postComment);

        postCommentRepository.deleteById(postCommentSeq);
    }

    /**
     * 게시글 댓글 전체 조회
     *
     * @param postSeq : 게시글 ID
     * @param pageable : 페이징 구현을 위한 Pageable 인자
     * @return Page<PostCommentDto>
     */
    public Page<PostCommentDto> getComments(Integer postSeq, Pageable pageable) {
        Post post = getPostOrException(postSeq);

        // post 전체 조회
        Page<PostComment> postCommentPage = postCommentRepository.findAllByPost(post, pageable);

        return postCommentPage.map(PostCommentDto::from);
    }

    /**
     * 게시글 존재 여부 체크
     *
     * @param postSeq : 게시글 ID
     * @return Post
     */
    private Post getPostOrException(Integer postSeq) {
        return postRepository.findById(postSeq).orElseThrow(
                () -> new ValidException(ValidErrorCode.POST_NOT_FOUND)
        );
    }

    /**
     * 회원 정보 존재 여부 체크
     *
     * @param userSeq : 회원 ID
     * @return User
     */
    private User getUserOrException(Integer userSeq) {
        return userRepository.findById(userSeq).orElseThrow(
                () -> new ValidException(ValidErrorCode.USER_NOT_FOUND)
        );
    }

    /**
     * 댓글 존재 여부 체크
     *
     * @param postCommentSeq : 댓글 ID
     * @return PostComment
     */
    private PostComment getPostCommentOrException(Integer postCommentSeq) {
        return postCommentRepository.findById(postCommentSeq).orElseThrow(
                () -> new ValidException(ValidErrorCode.POST_COMMENT_NOT_FOUND)
        );
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
     *
     * @param postCommentSeq
     * @param post
     * @param user
     * @param postComment
     */
    private void saveIfIsReplyComment(Integer postCommentSeq, Post post, User user, PostComment postComment) {
        if (postCommentSeq != null) {
            PostComment replyPostComment = getPostCommentOrException(postCommentSeq);

            validatePostCommentPostMatch(post, replyPostComment);
            validatePostCommentUserMatch(user, replyPostComment);

            postComment.setCommentSeq(replyPostComment);
            replyPostComment.getMyComments().add(postComment);
        }
    }

}
