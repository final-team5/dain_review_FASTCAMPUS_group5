package com.example.finalproject.domain.user.service;

import com.example.finalproject.domain.post.dto.PostDto;
import com.example.finalproject.domain.post.dto.request.InfluencerCreatePostRequest;
import com.example.finalproject.domain.post.entity.Post;
import com.example.finalproject.domain.post.entity.PostCategories;
import com.example.finalproject.domain.post.entity.PostTypes;
import com.example.finalproject.domain.post.entity.enums.PostCategory;
import com.example.finalproject.domain.post.repository.PostRepository;
import com.example.finalproject.domain.post.repository.PostTypesRepository;
import com.example.finalproject.domain.user.entity.User;
import com.example.finalproject.domain.user.repository.InfluencerRepository;
import com.example.finalproject.domain.user.repository.UserRepository;
import com.example.finalproject.global.exception.error.ValidErrorCode;
import com.example.finalproject.global.exception.type.CustomException;
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

    // 게시글 전체 조회 기능
    public List<PostDto> influencerGetAllPosts() {
        return postRepository.findAll().stream()
                .filter(post -> influencerRepository.existsByUser(post.getUser()))
                .map(PostDto::from)
                .collect(Collectors.toList());
    }

    // 게시글 생성 기능
    @Transactional
    public PostDto createInfluencerPost(InfluencerCreatePostRequest request, String useId) {
        User user = userRepository.getByStringId(useId);

        if (!influencerRepository.existsByUser(user)) {
            throw new CustomException(ValidErrorCode.INFLUENCER_NOT_FOUND);
        }

        PostTypes postTypes = postTypesRepository.getPostTypesByTypeOrException(request.getPostType());
        Post post = Post.of(user,
                new PostCategories(3, PostCategory.COMMUNITY_INFLUENCER),
                postTypes,
                request.getTitle(),
                request.getContents(),
                0);

        return PostDto.from(postRepository.save(post));
    }


    // 게시글 수정 기능
    @Transactional
    public PostDto updateInfluencerPost(Integer seq, Post postRequest, String useId) {
        User user = userRepository.getByStringId(useId);
        Post existingPost = postRepository.getPostBySeqOrException(seq);

        if (existingPost.isNotWriter(user)) {
            throw new CustomException(ValidErrorCode.POST_USER_MISMATCH);
        }

        if (!influencerRepository.existsByUser(user)) {
            throw new CustomException(ValidErrorCode.INFLUENCER_NOT_FOUND);
        }

        existingPost.setTitle(postRequest.getTitle());
        existingPost.setContents(postRequest.getContents());
        existingPost.setPostCategories(postRequest.getPostCategories());
        existingPost.setPostTypes(postRequest.getPostTypes());

        return PostDto.from(postRepository.save(existingPost));
    }

    // 게시글 삭제 기능
    @Transactional
    public void deleteInfluencerPost(Integer seq, String userId) {
        User user = userRepository.getByStringId(userId);
        Post post = postRepository.getPostBySeqOrException(seq);

        if (post.isNotWriter(user)) {
            throw new CustomException(ValidErrorCode.POST_USER_MISMATCH);
        }
        if (!influencerRepository.existsByUser(user)) {
            throw new CustomException(ValidErrorCode.INFLUENCER_NOT_FOUND);
        }

        postRepository.delete(post);
    }

    // 게시글 상세 조회 기능
    public void getInfluencerDetailPost(Integer seq, String userId) {
        User user = userRepository.getByStringId(userId);
        Post post = postRepository.getPostBySeqOrException(seq);

        if (post.isNotWriter(user)) {
            throw new CustomException(ValidErrorCode.POST_USER_MISMATCH);
        }
        if (!influencerRepository.existsByUser(user)) {
            throw new CustomException(ValidErrorCode.INFLUENCER_NOT_FOUND);
        }

        postRepository.findById(post.getSeq());
    }
}
