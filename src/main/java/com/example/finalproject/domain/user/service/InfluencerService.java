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
import com.example.finalproject.global.exception.type.CustomException;
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

    // 게시글 전체 조회 기능
    public List<Post> influencerGetAllPosts() {
        List<Post> allPosts = postRepository.findAll();
        List<Post> filteredPosts = allPosts.stream()
                .filter(this::isInfluencer)
                .collect(Collectors.toList());
        return filteredPosts;
    }

    // 게시글 생성 기능
    @Transactional
    public Post createInfluencerPost(Integer userSeq, Post postRequest) {
        User user = userRepository.findById(userSeq)
                .orElseThrow(() -> new CustomException(ValidErrorCode.USER_NOT_FOUND));

        if (!influencerRepository.existsByUser(user)) {
            throw new CustomException(ValidErrorCode.INFLUENCER_NOT_FOUND);
        }

        Post newPost = new Post(
                user,
                postRequest.getPostCategories(),
                postRequest.getPostTypes(),
                postRequest.getTitle(),
                postRequest.getContents(),
                0
        );

        return postRepository.save(newPost);
    }


    // 게시글 수정 기능
    @Transactional
    public Post updateInfluencerPost(Integer seq, Post postRequest) {
        Post existingPost = postRepository.findById(seq)
                .orElseThrow(() -> new CustomException(ValidErrorCode.POST_NOT_FOUND));

        if (!isInfluencer(existingPost)) {
            throw new CustomException(ValidErrorCode.INFLUENCER_NOT_FOUND);
        }

        existingPost.setTitle(postRequest.getTitle());
        existingPost.setContents(postRequest.getContents());
        existingPost.setPostCategories(postRequest.getPostCategories());
        existingPost.setPostTypes(postRequest.getPostTypes());

        return postRepository.save(existingPost);
    }

    // 게시글 삭제 기능
    @Transactional
    public void deleteInfluencerPost(Integer seq) {
        Post post = postRepository.findById(seq)
                .orElseThrow(() -> new CustomException(ValidErrorCode.POST_NOT_FOUND));

        if (!isInfluencer(post)) {
            throw new CustomException(ValidErrorCode.INFLUENCER_NOT_FOUND);
        }

        postRepository.delete(post);
    }

    // 게시판 조회 시 회원 중 인플루언서인지 검증 하는 코드
    private boolean isInfluencer(Post post) {
        User user = post.getUser();
        if (user == null) {
            throw new CustomException(ValidErrorCode.USER_NOT_FOUND);
        }
        return influencerRepository.existsByUser(user);
    }

}
