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
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostCommentResponse {

    private Integer postCommentSeq;

    private Integer replyCommentSeq;

    private String comment;

    private List<PostCommentDto> replyList;

    public static PostCommentResponse of(Integer postCommentSeq, Integer replyCommentSeq, String comment, List<PostCommentDto> replyList) {
        return new PostCommentResponse(postCommentSeq, replyCommentSeq, comment, replyList);
    }

    public static PostCommentResponse from(PostCommentDto postCommentDto) {
        return PostCommentResponse.of(
                postCommentDto.getSeq(),
                postCommentDto.getPostCommentSeq(),
                postCommentDto.getComment(),
                postCommentDto.getReplyComments().stream().map(PostCommentDto::from).collect(Collectors.toList())
        );
    }
}
