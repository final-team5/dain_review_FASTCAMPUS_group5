package com.example.finalproject.domain.post.dto;

import com.example.finalproject.domain.post.entity.PostComment;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostCommentDto {

    private Integer seq;

    private Integer userSeq;

    private Integer postSeq;

    private Integer postCommentSeq;

    private String comment;

    private List<PostComment> replyComments;

    public PostCommentDto(Integer seq, Integer userSeq, Integer postSeq, String comment, List<PostComment> replyComments) {
        this.seq = seq;
        this.userSeq = userSeq;
        this.postSeq = postSeq;
        this.comment = comment;
        this.replyComments = replyComments;
    }

    public static PostCommentDto of(Integer seq, Integer userSeq, Integer postSeq, Integer postCommentSeq, String comment, List<PostComment> replyComments) {
        return new PostCommentDto(seq, userSeq, postSeq, postCommentSeq, comment, replyComments);
    }

    public static PostCommentDto of(Integer seq, Integer userSeq, Integer postSeq, String comment, List<PostComment> replyComments) {
        return new PostCommentDto(seq, userSeq, postSeq, comment, replyComments);
    }

    public static PostCommentDto from(PostComment postComment) {
        return PostCommentDto.of(
                postComment.getSeq(),
                postComment.getUser().getSeq(),
                postComment.getPost().getSeq(),
                postComment.getComment(),
                postComment.getMyComments()
        );

    }
}
