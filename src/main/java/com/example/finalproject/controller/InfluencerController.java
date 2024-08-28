package com.example.finalproject.controller;
import com.example.finalproject.domain.post.entity.Post;
import com.example.finalproject.domain.user.service.InfluencerService;
import com.example.finalproject.global.util.ResponseApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Api(tags = "인플루언서")
@RestController
@RequestMapping("/inf/communities")
@RequiredArgsConstructor
public class InfluencerController {

    private final InfluencerService influencerService;


    // 게시글 전체 조회 기능
    @ApiOperation(value = "커뮤니티 리스트", tags = "인플루언서 - 커뮤니티")
    @GetMapping
    public ResponseApi<List<Post>> influencerGetAllPosts() {
        List<Post> posts = influencerService.influencerGetAllPosts();
        return ResponseApi.success(HttpStatus.OK, posts);
    }

    // 게시글 생성 기능
    @ApiOperation(value = "커뮤니티 글 생성", tags = "인플루언서 - 커뮤니티")
    @PostMapping
    public ResponseApi<Post> createInfluencerPost(
            @RequestParam Integer userSeq,
            @RequestBody Post postRequest) {
        Post createdPost = influencerService.createInfluencerPost(userSeq, postRequest);
        return ResponseApi.success(HttpStatus.CREATED, createdPost);
    }

    // 게시글 수정 기능
    @ApiOperation(value = "커뮤니티 글 수정", tags = "인플루언서 - 커뮤니티")
    @PutMapping("/{seq}")
    public ResponseApi<Post> updateInfluencerPost(
            @PathVariable Integer seq,
            @RequestBody Post postRequest) {
        Post updatedPost = influencerService.updateInfluencerPost(seq, postRequest);
        return ResponseApi.success(HttpStatus.OK, updatedPost);
    }

    // 게시글 삭제 기능
    @ApiOperation(value = "커뮤니티 글 삭제", tags = "인플루언서 - 커뮤니티")
    @DeleteMapping("/{seq}")
    public ResponseApi<String> deleteInfluencerPost(@PathVariable Integer seq) {
        influencerService.deleteInfluencerPost(seq);
        return ResponseApi.success(HttpStatus.NO_CONTENT, "Post deleted successfully");
    }

    // TODO : 인플루언서 게시글 상세 기능 구현 필요

}
