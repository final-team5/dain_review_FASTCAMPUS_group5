package com.example.finalproject.domain.post.dto.response;

import com.example.finalproject.domain.post.dto.PostDto;
import com.example.finalproject.domain.post.dto.PostWithCommentsDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.LowerCamelCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostFollowDetailResponse {

    private String category;
    private String nickname;
    private String title;
    private String contents;
    private Integer viewCount;
    private String registeredAt;
    private List<PostCommentDetailResponse> comments;

    public static PostFollowDetailResponse of(String category, String nickname, String title, String contents, Integer viewCount, String registeredAt, List<PostCommentDetailResponse> comments) {
        return new PostFollowDetailResponse(category, nickname, title, contents, viewCount, registeredAt, comments);
    }

//    public static PostFollowDetailResponse from(PostDto postDto) {
//        String registeredTime = postDto.getRegisteredAt().toString().replace('T', ' ');
//
//        return PostFollowDetailResponse.of(
//                postDto.getPostType(),
//                postDto.getUserDto().getId(),
//                postDto.getTitle(),
//                postDto.getContents(),
//                postDto.getViewCount(),
//                registeredTime
//        );
//    }

    public static PostFollowDetailResponse from(PostWithCommentsDto postWithCommentsDto) {
        PostDto postDto = postWithCommentsDto.getPostDto();

        String registeredTime = postDto.getRegisteredAt().toString().replace('T', ' ');

        return PostFollowDetailResponse.of(
                postDto.getPostType(),
                postDto.getUserDto().getId(),
                postDto.getTitle(),
                postDto.getContents(),
                postDto.getViewCount(),
                registeredTime,
                postWithCommentsDto.getCommentDtoList().stream().map(PostCommentDetailResponse::from).collect(Collectors.toList())
        );
    }
}
