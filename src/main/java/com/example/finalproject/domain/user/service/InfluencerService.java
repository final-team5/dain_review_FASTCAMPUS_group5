package com.example.finalproject.domain.user.service;

import com.example.finalproject.domain.post.dto.PostDto;
import com.example.finalproject.domain.post.entity.Post;
import com.example.finalproject.domain.post.entity.PostCategories;
import com.example.finalproject.domain.post.entity.PostTypes;
import com.example.finalproject.domain.post.entity.enums.PostCategory;
import com.example.finalproject.domain.post.entity.enums.PostType;
import com.example.finalproject.domain.post.repository.PostRepository;
import com.example.finalproject.domain.post.repository.PostTypesRepository;
import com.example.finalproject.domain.user.dto.UserInfluencerDto;
import com.example.finalproject.domain.user.entity.Influencer;
import com.example.finalproject.domain.user.entity.User;
import com.example.finalproject.domain.user.repository.InfluencerRepository;
import com.example.finalproject.domain.user.repository.UserRepository;
import com.example.finalproject.global.exception.error.ErrorCode;
import com.example.finalproject.global.exception.error.ValidErrorCode;
import com.example.finalproject.global.exception.type.ValidException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InfluencerService {

    private final PostRepository postRepository;
    private final PostTypesRepository postTypesRepository;
    private final UserRepository userRepository;
    private final InfluencerRepository influencerRepository;

    private ValidErrorCode validErrorCode;


    // 인플루언서 커뮤니티 게시글 전체 조회 기능
    public List<PostDto> getAllPostsByInfluencer(Integer seq){

        Influencer influencer = validateInfluencer(seq);
        List<Post> posts = postRepository.findAllByUser(influencer.getUser());

        return posts.stream()
                .map(PostDto::from)
                .collect(Collectors.toList());
    }

    // 인플루언서 커뮤니티 글 생성 기능
    @Transactional
    public PostDto createPost(Integer seq, String title, String contents, PostType postType) {

        Influencer influencer = validateInfluencer(seq);
        PostTypes postTypes = getPostTypesOrException(postType);
        PostCategories postCategories = PostCategories.of(4, PostCategory.EACH_OTHER_NEIGHBOR_FOLLOW);

        Post post = Post.of(influencer.getUser(), postCategories, postTypes, title, contents, 0);
        postRepository.save(post);

        return PostDto.from(post);
    }

    // 인플루언서 커뮤니티 글 수정 기능
    @Transactional
    public PostDto updatePost(Integer seq, Integer postSeq, String title, String contents, PostType postType) {

        Influencer influencer = validateInfluencer(seq);
        Post post = postRepository.findById(postSeq).orElseThrow(
                () -> new ValidException(ValidErrorCode.POST_NOT_FOUND)
        );
        validatePostUserMatch(influencer.getUser(), post);

        PostTypes postTypes = getPostTypesOrException(postType);
        post.setPostTypes(postTypes);
        post.setTitle(title);
        post.setContents(contents);

        return PostDto.from(post);
    }

    // 인플루언서 커뮤니티 글 삭제 기능
    @Transactional
    public void deletePost(Integer seq, Integer postSeq) {

        Influencer influencer = validateInfluencer(seq);
        Post post = postRepository.findById(postSeq).orElseThrow(
                () -> new ValidException(ValidErrorCode.POST_NOT_FOUND)
        );

        validatePostUserMatch(influencer.getUser(), post);
        postRepository.delete(post);
    }


    // 인플루언서 존재 여부와 본인 여부를 확인하는 메서드
    private Influencer validateInfluencer(Integer seq) {
        return influencerRepository.findById(seq)
                .orElseThrow(() -> new ValidException(ValidErrorCode.INFLUENCER_NOT_FOUND));
    }

    // 게시글 존재 여부 체크
    private Post getPostOrException(Integer seq) {
        return postRepository.findById(seq).orElseThrow(
                () -> new ValidException(ValidErrorCode.POST_NOT_FOUND)
        );
    }

    // 회원 본인이 작성한 게시글인지 체크
    private void validatePostUserMatch(User user, Post post) {
        if (!user.getSeq().equals(post.getUser().getSeq())) {
            throw new ValidException(ValidErrorCode.POST_USER_MISMATCH);
        }
    }

    // PostType 존재 여부 체크
    private PostTypes getPostTypesOrException(PostType postType) {
        return postTypesRepository.findByType(postType).orElseThrow(
                () -> new ValidException(ValidErrorCode.POST_TYPE_NOT_FOUND)
        );
    }

}
