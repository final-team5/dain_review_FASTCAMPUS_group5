package com.example.finalproject.domain.post.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommunityPostDeleteRequest {
    private List<Integer> seqs;
}