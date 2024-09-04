package com.example.finalproject.domain.post.dto.request;

import com.example.finalproject.domain.post.entity.enums.PostType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.LowerCamelCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostUpdateRequest {

    @NotNull
    private Integer seq;

    @NotBlank
    private PostType category;

    @NotBlank
    private String title;

    @NotBlank
    private String contents;
}
