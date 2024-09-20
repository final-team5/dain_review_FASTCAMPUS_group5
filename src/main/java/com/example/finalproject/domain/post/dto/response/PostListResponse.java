package com.example.finalproject.domain.post.dto.response;

import com.example.finalproject.domain.post.dto.PostDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.LowerCamelCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostListResponse {
    private Integer seq;
    private String postType;
    private String title;
    private String contents;
    private String username;
    private String registeredAt;
    private Integer viewCount;
    private Integer commentCount;

    public static PostListResponse of(Integer seq, String postType, String title, String contents, String username, String registeredAt, Integer viewCount, Integer commentCount) {
        return new PostListResponse(seq, postType, title, contents, username, registeredAt, viewCount, commentCount);
    }

    public static PostListResponse from(PostDto postDto) {
        String[] split = postDto.getRegisteredAt().toString().split("T");
        String registeredDate = split[0];

        return PostListResponse.of(
                postDto.getSeq(),
                postDto.getPostType(),
                postDto.getTitle(),
                postDto.getContents(),
                postDto.getUserDto().getName(),
                registeredDate,
                postDto.getViewCount(),
                postDto.getCommentCount()
        );
    }
}
