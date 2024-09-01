package com.example.finalproject.domain.user.service;
import com.example.finalproject.domain.post.entity.Post;
import com.example.finalproject.domain.post.repository.PostRepository;
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

    // 게시판 조회 시 회원 중 인플루언서인지 검증 하는 코드
    private boolean isInfluencer(Post post) {
        User user = post.getUser();
        if (user == null) {
            throw new CustomException(ValidErrorCode.USER_NOT_FOUND);
        }
        return influencerRepository.existsByUser(user);
    }

}
