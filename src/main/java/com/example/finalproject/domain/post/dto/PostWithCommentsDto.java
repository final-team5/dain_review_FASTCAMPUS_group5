package com.example.finalproject.domain.post.dto;

import com.example.finalproject.domain.post.entity.Post;
import com.example.finalproject.domain.post.entity.PostComment;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.LowerCamelCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostWithCommentsDto {

    private PostDto postDto;

    private Page<PostCommentDto> commentDtoList;

    public static PostWithCommentsDto of(PostDto postDto, Page<PostCommentDto> commentDtoList) {
        return new PostWithCommentsDto(postDto, commentDtoList);
    }

    public static PostWithCommentsDto from(Post post, Page<PostComment> postComments) {
        return PostWithCommentsDto.of(
                PostDto.from(post),
                postComments.map(PostCommentDto::from)
        );
    }

}
