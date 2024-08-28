package com.example.finalproject.domain.post.dto;

import com.example.finalproject.domain.post.entity.Post;
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
public class PostWithCommentsDto {

    private PostDto postDto;

    private List<PostCommentDto> commentDtoList;

    public static PostWithCommentsDto of(PostDto postDto, List<PostCommentDto> commentDtoList) {
        return new PostWithCommentsDto(postDto, commentDtoList);
    }

    public static PostWithCommentsDto from(Post post) {
        return PostWithCommentsDto.of(
                PostDto.from(post),
                post.getComments().stream().map(PostCommentDto::from).collect(Collectors.toList())
        );
    }
}
