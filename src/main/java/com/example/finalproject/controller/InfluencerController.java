package com.example.finalproject.controller;
import com.example.finalproject.domain.post.dto.PostDto;
import com.example.finalproject.domain.post.dto.request.InfluencerCreatePostRequest;
import com.example.finalproject.domain.post.entity.Post;
import com.example.finalproject.domain.user.service.InfluencerService;
import com.example.finalproject.global.util.ResponseApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Api(tags = "인플루언서")
@RestController
@RequestMapping("/inf/communities")
@RequiredArgsConstructor
public class InfluencerController {

    private InfluencerService influencerService;



    // 게시글 전체 조회 기능
    @ApiOperation(value = "커뮤니티 리스트", tags = "인플루언서 - 커뮤니티")
    @GetMapping
    public ResponseApi<List<PostDto>> influencerGetAllPosts() {
        List<PostDto> posts = influencerService.influencerGetAllPosts();
        return ResponseApi.success(HttpStatus.OK, posts);
    }

    // 게시글 생성 기능
    @ApiOperation(value = "커뮤니티 글 생성", tags = "인플루언서 - 커뮤니티")
    @PostMapping
    public ResponseApi<PostDto> createInfluencerPost(
            @AuthenticationPrincipal UserDetails details,
            @RequestBody InfluencerCreatePostRequest influencerCreatePostRequest
    )
    {
        PostDto postDto = influencerService.createInfluencerPost(influencerCreatePostRequest, details.getUsername());

        return ResponseApi.success(HttpStatus.CREATED, postDto);
    }

    // 게시글 수정 기능
    @ApiOperation(value = "커뮤니티 글 수정", tags = "인플루언서 - 커뮤니티")
    @PutMapping("/{seq}")
    public ResponseApi<PostDto> updateInfluencerPost(
            @PathVariable Integer seq,
            @AuthenticationPrincipal UserDetails details,
            @RequestBody Post postRequest)
    {
        PostDto updatedPost = influencerService.updateInfluencerPost(seq, postRequest, details.getUsername());
        return ResponseApi.success(HttpStatus.OK, updatedPost);
    }

    // 게시글 삭제 기능
    @ApiOperation(value = "커뮤니티 글 삭제", tags = "인플루언서 - 커뮤니티")
    @DeleteMapping("/{seq}")
    public ResponseApi<String> deleteInfluencerPost(
            @AuthenticationPrincipal UserDetails details,
            @PathVariable Integer seq)
    {
        influencerService.deleteInfluencerPost(seq, details.getUsername());
        return ResponseApi.success(HttpStatus.NO_CONTENT, "Post deleted successfully");
    }

    // 게시글 상세 조회 기능
    // ResponseDto 필요
    @ApiOperation(value = "커뮤니티 글 상세 조회", tags = "인플루언서 - 커뮤니티")
    @GetMapping("/{seq}/")
    public ResponseApi<?> getInfluencerDeteailPost(
            @AuthenticationPrincipal UserDetails details,
            @PathVariable Integer seq
            )
    {
        influencerService.getInfluencerDeteailPost(seq,details.getUsername());
        return ResponseApi.success(HttpStatus.OK,"Get influencer deteail post successfully");
    }

}
