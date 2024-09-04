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
public class PostResponse {

    private Integer postSeq;
    private Integer userSeq;
    private String postCategory;
    private String postType;
    private String title;
    private String contents;

    public static PostResponse of(Integer postSeq, Integer userSeq, String postCategory, String postType, String title, String contents) {
        return new PostResponse(postSeq, userSeq, postCategory, postType, title, contents);
    }

    public static PostResponse from(PostDto postDto) {
        return PostResponse.of(
                postDto.getSeq(),
                postDto.getUserDto().getSeq(),
                postDto.getPostCategory(),
                postDto.getPostType(),
                postDto.getTitle(),
                postDto.getContents()
        );
    }
}
