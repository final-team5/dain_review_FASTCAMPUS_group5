package com.example.finalproject.domain.post.service;

import com.example.finalproject.domain.post.dto.PostDto;
import com.example.finalproject.domain.post.entity.Post;
import com.example.finalproject.domain.post.entity.PostCategories;
import com.example.finalproject.domain.post.entity.PostTypes;
import com.example.finalproject.domain.post.entity.enums.PostCategory;
import com.example.finalproject.domain.post.entity.enums.PostType;
import com.example.finalproject.domain.post.entity.enums.SearchType;
import com.example.finalproject.domain.post.repository.PostRepository;
import com.example.finalproject.domain.post.repository.PostTypesRepository;
import com.example.finalproject.domain.user.entity.User;
import com.example.finalproject.domain.user.repository.UserRepository;
import com.example.finalproject.global.exception.error.ValidErrorCode;
import com.example.finalproject.global.exception.type.ValidException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// TODO : getOr~ 메서드들 따로 객체지향적으로 분리
@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
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
        User user = getUserOrException(userSeq);

        PostTypes postTypes = getPostTypesOrException(category);

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
        User user = getUserOrException(userSeq);

        PostTypes postTypes = getPostTypesOrException(category);

        PostCategories postCategories = PostCategories.of(4, PostCategory.EACH_OTHER_NEIGHBOR_FOLLOW);

        Post post = getPostOrException(seq);

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
    public void deleteFollowPost(Integer seq, Integer userSeq) {
        User user = getUserOrException(userSeq);

        Post post = getPostOrException(seq);

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
    public PostDto findDetailFollowPost(Integer seq, Integer userSeq) {
        User user = getUserOrException(userSeq);

        Post post = getPostOrException(seq);

        return PostDto.from(post);
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
            return postRepository.findAll(pageable).map(PostDto::from);
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
     * 회원 정보 존재 여부 체크
     *
     * @param category : sns 카테고리
     * @return PostTypes
     */
    private PostTypes getPostTypesOrException(PostType category) {
        return postTypesRepository.findByType(category).orElseThrow(
                () -> new ValidException(ValidErrorCode.POST_TYPE_NOT_FOUND)
        );
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
