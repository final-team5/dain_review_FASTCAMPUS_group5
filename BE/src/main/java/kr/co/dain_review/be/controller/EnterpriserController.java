package kr.co.dain_review.be.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import kr.co.dain_review.be.model.jwt.TokenProvider;
import kr.co.dain_review.be.model.list.Delete;
import kr.co.dain_review.be.model.list.Search;
import kr.co.dain_review.be.model.post.PostInsert;
import kr.co.dain_review.be.model.post.PostUpdate;
import kr.co.dain_review.be.model.product.*;
import kr.co.dain_review.be.service.PostService;
import kr.co.dain_review.be.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Api(tags = "사업자")
@RequestMapping("/ent")
@RequiredArgsConstructor
@RestController
public class EnterpriserController {

    private final ProductService productService;
    private final TokenProvider tokenProvider;
    private final PostService postService;

    @ApiOperation(value = "내 체험단 검색", tags = "사업자 - 체험단")
    @GetMapping("/my-product")
    public ResponseEntity<?> product(@RequestHeader HttpHeaders header, ProductSearch search){
        String token = header.getFirst("Authorization");
        Integer userSeq = tokenProvider.getSeq(token);
        JSONObject json = new JSONObject();
        json.put("message", "SUCCESS");
        json.put("list", productService.getList(search, userSeq));
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "체험단 모집", tags = "사업자 - 체험단")
    @PostMapping("/product")
    public ResponseEntity<?> product(@RequestHeader HttpHeaders header, @RequestBody ProductInsert insert){
        String token = header.getFirst("Authorization");
        Integer userSeq = tokenProvider.getSeq(token);
        productService.setInsert(insert, userSeq);
        JSONObject json = new JSONObject();
        json.put("message", "SUCCESS");
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "체험단 진행 상세", tags = "사업자 - 체험단")
    @GetMapping("/product-detail/{seq}")
    public ResponseEntity<?> product(@RequestHeader HttpHeaders header, @PathVariable Integer seq){
        String token = header.getFirst("Authorization");
        Integer userSeq = tokenProvider.getSeq(token);
        return new ResponseEntity<>(productService.getDetail(seq, userSeq), HttpStatus.OK);
    }

    @ApiOperation(value = "신청한 체험단 목록", tags = "사업자 - 체험단")
    @GetMapping("/applicationInfluencer/{seq}")
    public ResponseEntity<?> applicationInfluencer(@RequestHeader HttpHeaders header, @PathVariable Integer seq){
        String token = header.getFirst("Authorization");
        Integer userSeq = tokenProvider.getSeq(token);
        JSONObject json = new JSONObject();
        if(!productService.myProductCheck(userSeq, seq)){
            json.put("message", "확인 권한이 없습니다");
            return new ResponseEntity<>(json.toString(), HttpStatus.OK);
        }
        json.put("list", productService.applicationInfluencer(seq));
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "선정한 체험단 목록", tags = "사업자 - 체험단")
    @GetMapping("/selectionInfluencer/{seq}")
    public ResponseEntity<?> selectionInfluencer(@RequestHeader HttpHeaders header, @PathVariable Integer seq){
        String token = header.getFirst("Authorization");
        Integer userSeq = tokenProvider.getSeq(token);
        JSONObject json = new JSONObject();
        if(!productService.myProductCheck(userSeq, seq)){
            json.put("message", "확인 권한이 없습니다");
            return new ResponseEntity<>(json.toString(), HttpStatus.OK);
        }
        json.put("list", productService.selectionInfluencer(seq));
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "체험단 선정", tags = "사업자 - 체험단")
    @PostMapping("/select")
    public ResponseEntity<?> product_(@RequestHeader HttpHeaders header, @RequestBody InfluencerSelect select) {
        String token = header.getFirst("Authorization");
        Integer userSeq = tokenProvider.getSeq(token);
        if(productService.myProductCheck(userSeq, select.getProductSeq())) {
            productService.InfluencerSelect(select);
        }
        JSONObject json = new JSONObject();
        json.put("message", "SUCCESS");
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "체험 진행하기", tags = "사업자 - 체험단")
    @PostMapping("/product/{seq}")
    public ResponseEntity<?> product_(@RequestHeader HttpHeaders header, @PathVariable Integer seq){
        String token = header.getFirst("Authorization");
        Integer userSeq = tokenProvider.getSeq(token);
        productService.setProgress(seq, userSeq);
        JSONObject json = new JSONObject();
        json.put("message", "SUCCESS");
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "신청 인플루언서 목록 다운로드", tags = "사업자 - 체험단")
    @PostMapping("/download/{seq}")
    public ResponseEntity<?> download(@RequestHeader HttpHeaders header, @PathVariable Integer seq){
        String token = header.getFirst("Authorization");
        Integer userSeq = tokenProvider.getSeq(token);
        productService.setProgress(seq, userSeq);
        JSONObject json = new JSONObject();
        json.put("message", "SUCCESS");
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }


    @ApiOperation(value = "커뮤니티 리스트", tags = "사업자 - 커뮤니티")
    @GetMapping("/post")
    public ResponseEntity<?> community(@RequestHeader HttpHeaders header, Search search){
        JSONObject json = new JSONObject();
        json.put("list", postService.select(search, 2));
        json.put("totalCount", postService.selectCount(search, 2));
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "커뮤니티 상세", tags = "사업자 - 커뮤니티")
    @GetMapping("/post/{seq}")
    public ResponseEntity<?> community(@RequestHeader HttpHeaders header, @PathVariable Integer seq){
        JSONObject json = new JSONObject();
        json.put("list", postService.selectDetail(seq, 2));
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "커뮤니티 글 추가", tags = "사업자 - 커뮤니티")
    @PostMapping("/post")
    public ResponseEntity<?> community(@RequestHeader HttpHeaders header, @RequestBody PostInsert insert){
        String token = header.getFirst("Authorization");
        Integer userSeq = tokenProvider.getSeq(token);
        postService.insert(insert, 2, userSeq);
        JSONObject json = new JSONObject();
        json.put("message", "SUCCESS");
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "커뮤니티 글 수정", tags = "사업자 - 커뮤니티")
    @PutMapping("/post")
    public ResponseEntity<?> community(@RequestHeader HttpHeaders header, @RequestBody PostUpdate update){
        String token = header.getFirst("Authorization");
        Integer userSeq = tokenProvider.getSeq(token);
        postService.update(update, userSeq);
        JSONObject json = new JSONObject();
        json.put("message", "SUCCESS");
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }

    @ApiOperation(value = "커뮤니티 글 삭제", tags = "사업자 - 커뮤니티")
    @DeleteMapping("/post")
    public ResponseEntity<?> community(@RequestHeader HttpHeaders header, @RequestBody Delete delete){
        String token = header.getFirst("Authorization");
        Integer userSeq = tokenProvider.getSeq(token);
        postService.delete(delete, userSeq);
        JSONObject json = new JSONObject();
        json.put("message", "SUCCESS");
        return new ResponseEntity<>(json.toString(), HttpStatus.OK);
    }
}
