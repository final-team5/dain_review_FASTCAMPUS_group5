package com.example.finalproject.controller;

import com.example.finalproject.domain.post.dto.PostDto;
import com.example.finalproject.domain.post.entity.enums.PostType;
import com.example.finalproject.domain.user.service.InfluencerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "인플루언서")
@RestController
@RequestMapping("/inf")
@RequiredArgsConstructor
public class InfluencerController {

    private final InfluencerService influencerService;


    @ApiOperation(value = "커뮤니티 리스트", tags = "인플루언서 - 커뮤니티")
    @GetMapping("/{seq}/posts")
    public ResponseEntity<List<PostDto>> getAllPosts(@PathVariable Integer seq) {
        List<PostDto> posts = influencerService.getAllPostsByInfluencer(seq);
        return ResponseEntity.ok(posts);
    }


    @ApiOperation(value = "커뮤니티 글 생성", tags = "인플루언서 - 커뮤니티")
    @PostMapping("/{seq}/posts")
    public ResponseEntity<PostDto> createPost(@PathVariable Integer seq, @RequestBody PostDto postDto) {
        PostDto createdPost = influencerService.createPost(seq, postDto.getTitle(), postDto.getContents(), PostType.valueOf(postDto.getPostType()));
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPost);
    }


    @ApiOperation(value = "커뮤니티 글 수정", tags = "인플루언서 - 커뮤니티")
    @PutMapping("/{seq}/posts/{postSeq}")
    public ResponseEntity<PostDto> updatePost(@PathVariable Integer seq, @PathVariable Integer postSeq, @RequestBody PostDto postDto) {
        PostDto updatedPost = influencerService.updatePost(seq, postSeq, postDto.getTitle(), postDto.getContents(), PostType.valueOf(postDto.getPostType()));
        return ResponseEntity.ok(updatedPost);
    }


    @ApiOperation(value = "커뮤니티 글 삭제", tags = "인플루언서 - 커뮤니티")
    @DeleteMapping("/{seq}/posts/{postSeq}")
    public ResponseEntity<Void> deletePost(@PathVariable Integer seq, @PathVariable Integer postSeq) {
        influencerService.deletePost(seq, postSeq);
        return ResponseEntity.noContent().build();
    }


}
