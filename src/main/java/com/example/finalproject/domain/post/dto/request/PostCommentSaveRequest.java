package com.example.finalproject.domain.post.dto.request;

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
public class PostCommentSaveRequest {

    private Integer postSeq;

    private Integer commentSeq;

    private String comment;

    public static PostCommentSaveRequest of(Integer postSeq, Integer commentSeq, String comment) {
        return new PostCommentSaveRequest(postSeq, commentSeq, comment);
    }
}
