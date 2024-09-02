package com.example.finalproject.domain.post.service;

import com.example.finalproject.domain.post.dto.PostDto;
import com.example.finalproject.domain.post.dto.PostWithCommentsDto;
import com.example.finalproject.domain.post.dto.request.PostSaveRequest;
import com.example.finalproject.domain.post.dto.request.PostUpdateRequest;
import com.example.finalproject.domain.post.entity.Post;
import com.example.finalproject.domain.post.entity.PostCategories;
import com.example.finalproject.domain.post.entity.PostComment;
import com.example.finalproject.domain.post.entity.PostTypes;
import com.example.finalproject.domain.post.entity.enums.PostCategory;
import com.example.finalproject.domain.post.entity.enums.PostType;
import com.example.finalproject.domain.post.entity.enums.SearchType;
import com.example.finalproject.domain.post.repository.PostCommentRepository;
import com.example.finalproject.domain.post.repository.PostRepository;
import com.example.finalproject.domain.post.repository.PostTypesRepository;
import com.example.finalproject.domain.user.entity.User;
import com.example.finalproject.domain.user.repository.UserRepository;
import com.example.finalproject.global.exception.error.ValidErrorCode;
import com.example.finalproject.global.exception.type.ValidException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final PostCommentRepository postCommentRepository;
    private final UserRepository userRepository;
    private final PostTypesRepository postTypesRepository;

    /**
     * 서이추/맞팔 게시판 게시글 등록
     *
     * @param category : sns 종류
     * @param contents : 게시글 본문 내용
     * @param title : 게시글 제목
     * @param userSeq : 로그인한 사용자 ID
     * @return PostDto
     */
    @Transactional
    public PostDto saveFollowPost(PostType category, String contents, String title, Integer userSeq) {
        User user = userRepository.getUserBySeqOrException(userSeq);

        PostTypes postTypes = postTypesRepository.getPostTypesByTypeOrException(category);

        PostCategories postCategories = PostCategories.of(4, PostCategory.EACH_OTHER_NEIGHBOR_FOLLOW);

        Post post = Post.of(user, postCategories, postTypes, title, contents, 0);

        Post savedPost = postRepository.save(post);

        return PostDto.from(savedPost);
    }

    /**
     * 서이추/맞팔 게시판 게시글 수정
     *
     * @param seq : 게시글 ID
     * @param category : sns 카테고리
     * @param contents : 게시글 본문
     * @param title : 게시글 제목
     * @param userSeq : 로그인한 사용자 ID
     * @return PostDto
     */
    @Transactional
    public PostDto updateFollowPost(Integer seq, PostType category, String contents, String title, Integer userSeq) {
        User user = userRepository.getUserBySeqOrException(userSeq);

        PostTypes postTypes = postTypesRepository.getPostTypesByTypeOrException(category);

        PostCategories postCategories = PostCategories.of(4, PostCategory.EACH_OTHER_NEIGHBOR_FOLLOW);

        Post post = postRepository.getPostBySeqOrException(seq);

        validatePostUserMatch(user, post);

        post.setPostTypes(postTypes);
        post.setTitle(title);
        post.setContents(contents);

        return PostDto.from(post);
    }

    /**
     * 서이추/맞팔 게시글 삭제
     *
     * @param seq : 삭제할 게시글 ID
     * @param userSeq : 로그인한 사용자 ID
     */
    @Transactional
    public void deletePost(Integer seq, Integer userSeq) {
        User user = userRepository.getUserBySeqOrException(userSeq);

        Post post = postRepository.getPostBySeqOrException(seq);

        validatePostUserMatch(user, post);

        postRepository.deleteById(seq);
    }

    /**
     * 서이추/맞팔 게시글 상세 조회
     *
     * @param seq : 조회할 게시글 ID
     * @param userSeq : 로그인한 사용자 ID
     * @return PostDto
     */
    public PostWithCommentsDto findDetailFollowPost(Integer seq, Integer userSeq, Pageable pageable) {
        User user = userRepository.getUserBySeqOrException(userSeq);

        Post post = postRepository.getPostBySeqOrException(seq);
        Page<PostComment> postComments = postCommentRepository.findAllByPost(post, pageable);

        return PostWithCommentsDto.from(post, postComments);
    }

    /**
     * 조회수 증가 서비스 로직
     *
     * @param seq : 조회된 게시글 ID
     */
    @Transactional
    public void updateViewCounts(Integer seq) {
        postRepository.updateViewCounts(seq);
    }

    /**
     * 서이추/맞팔 게시판 리스트 목록 조회
     *
     * @param searchType : 검색어 조건
     * @param searchWord : 검색어
     * @param pageable : 페이징
     * @return Page<PostDto>
     */
    // TODO : 추후 인플루언서, 사업주 엔티티 설계 완료 시, 반환 내용 닉네임 및 업체명으로 수정해야함.
    public Page<PostDto> findListFollowPost(SearchType searchType, String searchWord, Pageable pageable) {
        if (searchWord == null || searchWord.isEmpty()) {
            return postRepository.findAllByCategorySeq(4, pageable).map(PostDto::from);
        }

        if (searchType == null) {
            return postRepository.findByContaining(4, searchWord, pageable).map(PostDto::from);
        }

        switch (searchType) {
            case ALL:
                return postRepository.findByContaining(4, searchWord, pageable).map(PostDto::from);
            case USER:
                return postRepository.findByUsernameContaining(4, searchWord, pageable).map(PostDto::from);
            case TITLE:
                return postRepository.findByTitleContaining(4, searchWord, pageable).map(PostDto::from);
            case CONTENTS:
                return postRepository.findByContentsContaining(4, searchWord, pageable).map(PostDto::from);
            default:
                throw new ValidException(ValidErrorCode.POST_SEARCH_TYPE_NOT_FOUND);
        }
    }

    /**
     * 인플루언서 커뮤니티 게시글 등록 기능.
     *
     * @param postSaveRequest : 게시글 등록 요청 정보
     * @param userSeq : 로그인한 사용자 ID 값
     * @return PostDto
     */
    @Transactional
    public PostDto saveInfCommunityPost(PostSaveRequest postSaveRequest, Integer userSeq) {
        User user = userRepository.getUserBySeqOrException(userSeq);

        PostTypes postTypes = postTypesRepository.getPostTypesByTypeOrException(postSaveRequest.getCategory());

        PostCategories postCategories = PostCategories.of(3, PostCategory.COMMUNITY_INFLUENCER);

        Post post = Post.of(user, postCategories, postTypes, postSaveRequest.getTitle(), postSaveRequest.getContents(), 0);

        Post savedPost = postRepository.save(post);

        return PostDto.from(savedPost);
    }

    /**
     * 인플루언서 커뮤니티 게시글 수정 기능.
     *
     * @param postUpdateRequest : 게시글 수정 정보
     * @param userSeq : 로그인한 사용자 ID 값
     * @return PostDto
     */
    @Transactional
    public PostDto updateInfCommunityPost(PostUpdateRequest postUpdateRequest, Integer userSeq) {
        User user = userRepository.getUserBySeqOrException(userSeq);

        PostTypes postTypes = postTypesRepository.getPostTypesByTypeOrException(postUpdateRequest.getCategory());

        PostCategories postCategories = PostCategories.of(3, PostCategory.COMMUNITY_INFLUENCER);

        Post post = postRepository.getPostBySeqOrException(postUpdateRequest.getSeq());

        validatePostUserMatch(user, post);

        post.setPostTypes(postTypes);
        post.setTitle(postUpdateRequest.getTitle());
        post.setContents(postUpdateRequest.getContents());

        return PostDto.from(post);
    }


    /**
     * 회원 본인이 작성한 게시글인지 체크
     *
     * @param user : 회원 정보
     * @param post : 댓글 정보
     */
    private void validatePostUserMatch(User user, Post post) {
        if (!user.getSeq().equals(post.getUser().getSeq())) {
            throw new ValidException(ValidErrorCode.POST_USER_MISMATCH);
        }
    }
}
