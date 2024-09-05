package com.example.finalproject.domain.post.dto.response;

import com.example.finalproject.domain.post.dto.PostCommentDto;
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
public class PostCommentResponse {

    private Integer seq;

    private String comment;

    public static PostCommentResponse of(Integer seq, String comment) {
        return new PostCommentResponse(seq, comment);
    }

    public static PostCommentResponse from(PostCommentDto postCommentDto) {
        return PostCommentResponse.of(
                postCommentDto.getSeq(),
                postCommentDto.getComment()
        );
    }
}
