package com.example.finalproject.domain.post.dto.request;

import com.example.finalproject.domain.post.entity.enums.PostType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class InfluencerCreatePostRequest {

    @NotBlank
    private PostType postType;

    @NotBlank
    private String contents;

    @Size(min = 1, max = 100)
    @NotBlank
    private String title;

}
