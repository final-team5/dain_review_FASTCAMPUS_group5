package com.example.finalproject.domain.post.service;

import com.example.finalproject.domain.post.dto.PostDto;
import com.example.finalproject.domain.post.entity.Post;
import com.example.finalproject.domain.post.entity.PostCategories;
import com.example.finalproject.domain.post.entity.PostTypes;
import com.example.finalproject.domain.post.entity.enums.PostCategory;
import com.example.finalproject.domain.post.entity.enums.PostType;
import com.example.finalproject.domain.post.repository.PostRepository;
import com.example.finalproject.domain.post.repository.PostTypesRepository;
import com.example.finalproject.domain.user.entity.User;
import com.example.finalproject.domain.user.repository.UserRepository;
import com.example.finalproject.global.exception.error.ValidErrorCode;
import com.example.finalproject.global.exception.type.ValidException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostTypesRepository postTypesRepository;

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
}
