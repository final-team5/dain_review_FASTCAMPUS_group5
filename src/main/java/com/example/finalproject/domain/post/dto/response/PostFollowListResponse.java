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
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostFollowListResponse {
    private String postType;
    private String title;
    private String username;
    private String registeredAt;
    private Integer viewCount;

    public static PostFollowListResponse of(String postType, String title, String username, String registeredAt, Integer viewCount) {
        return new PostFollowListResponse(postType, title, username, registeredAt, viewCount);
    }

    public static PostFollowListResponse from(PostDto postDto) {
        String[] split = postDto.getRegisteredAt().toString().split("T");
        String registeredDate = split[0];

        return PostFollowListResponse.of(
                postDto.getPostType(),
                postDto.getTitle(),
                postDto.getUserDto().getName(),
                registeredDate,
                postDto.getViewCount()
        );
    }
}
