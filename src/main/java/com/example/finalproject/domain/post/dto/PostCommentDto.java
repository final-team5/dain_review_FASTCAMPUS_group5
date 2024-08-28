package com.example.finalproject.domain.post.dto;

import com.example.finalproject.domain.post.entity.PostComment;
import com.example.finalproject.domain.user.dto.UserDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.LowerCamelCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostCommentDto {

    private Integer seq;

    private UserDto userDto;

    private Integer postSeq;

    private Integer postCommentSeq;

    private String comment;

    private Timestamp registeredAt;

    public PostCommentDto(Integer seq, UserDto userDto, Integer postSeq, String comment, Timestamp registeredAt) {
        this.seq = seq;
        this.userDto = userDto;
        this.postSeq = postSeq;
        this.comment = comment;
        this.registeredAt = registeredAt;
    }

    public static PostCommentDto of(Integer seq, UserDto userDto, Integer postSeq, Integer postCommentSeq, String comment, Timestamp registeredAt) {
        return new PostCommentDto(seq, userDto, postSeq, postCommentSeq, comment, registeredAt);
    }

    public static PostCommentDto of(Integer seq, UserDto userDto, Integer postSeq, String comment, Timestamp registeredAt) {
        return new PostCommentDto(seq, userDto, postSeq, comment, registeredAt);
    }

    public static PostCommentDto from(PostComment postComment) {
        return PostCommentDto.of(
                postComment.getSeq(),
                UserDto.from(postComment.getUser()),
                postComment.getPost().getSeq(),
                postComment.getComment(),
                postComment.getRegisteredAt()
        );

    }
}
