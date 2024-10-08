package com.example.finalproject.domain.post.dto;

import com.example.finalproject.domain.post.entity.Post;
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
public class PostDto {
    private Integer seq;

    private UserDto userDto;

    private String postCategory;

    private String postType;

    private String title;

    private String contents;

    private Integer viewCount;

    private Integer commentCount;

    private Timestamp registeredAt;

    private Timestamp updatedAt;

    public static PostDto of(Integer seq, UserDto userDto, String postCategory, String postType, String title, String contents, Integer viewCount, Integer commentCount, Timestamp registeredAt, Timestamp updatedAt) {
        return new PostDto(seq, userDto, postCategory, postType, title, contents, viewCount, commentCount, registeredAt, updatedAt);
    }

    public static PostDto from(Post post) {
        return PostDto.of(
                post.getSeq(),
                UserDto.from(post.getUser()),
                post.getPostCategories().getCategory().getInfo(),
                post.getPostTypes().getType().getInfo(),
                post.getTitle(),
                post.getContents(),
                post.getViewCount(),
                post.getComments().size(),
                post.getRegisteredAt(),
                post.getUpdatedAt()
        );
    }

}
