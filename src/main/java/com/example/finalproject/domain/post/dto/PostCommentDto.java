package com.example.finalproject.domain.post.dto;

import com.example.finalproject.domain.post.entity.PostComment;
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
public class PostCommentDto {

    private Integer seq;

    private Integer userSeq;

    private Integer postSeq;

    private String comment;

    public static PostCommentDto of(Integer seq, Integer userSeq, Integer postSeq, String comment) {
        return new PostCommentDto(seq, userSeq, postSeq, comment);
    }

    public static PostCommentDto from(PostComment postComment) {
        return PostCommentDto.of(
                postComment.getSeq(),
                postComment.getUser().getSeq(),
                postComment.getPost().getSeq(),
                postComment.getComment()
        );
    }
}
