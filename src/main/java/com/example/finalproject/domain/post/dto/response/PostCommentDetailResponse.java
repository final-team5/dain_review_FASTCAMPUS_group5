package com.example.finalproject.domain.post.dto.response;

import com.example.finalproject.domain.post.dto.PostCommentDto;
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
public class PostCommentDetailResponse {

    private Integer seq;

    private String username;

    private String comment;

    private Timestamp registeredAt;

    public static PostCommentDetailResponse of(Integer seq, String username, String comment, Timestamp registeredAt) {
        return new PostCommentDetailResponse(seq, username, comment, registeredAt);
    }

    public static PostCommentDetailResponse from(PostCommentDto postCommentDto) {
        return PostCommentDetailResponse.of(
                postCommentDto.getSeq(),
                postCommentDto.getUserDto().getName(),
                postCommentDto.getComment(),
                postCommentDto.getRegisteredAt()
        );
    }
}
