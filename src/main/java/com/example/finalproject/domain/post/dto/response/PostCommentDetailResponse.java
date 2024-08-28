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
public class PostCommentDetailResponse {

    private Integer seq;

    private String username;

    private String comment;

    private String registeredAt;

    private List<PostCommentDetailResponse> myComments;

    public static PostCommentDetailResponse of(Integer seq, String username, String comment, String registeredAt, List<PostCommentDetailResponse> myComments) {
        return new PostCommentDetailResponse(seq, username, comment, registeredAt, myComments);
    }

    public static PostCommentDetailResponse from(PostCommentDto postCommentDto) {
        return PostCommentDetailResponse.of(
                postCommentDto.getSeq(),
                postCommentDto.getUserDto().getName(),
                postCommentDto.getComment(),
                postCommentDto.getRegisteredAt(),
                postCommentDto.getMyComments().stream().map(PostCommentDetailResponse::from).collect(Collectors.toList())
        );
    }
}
