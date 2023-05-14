package com.politics_moorim.controller;

import com.politics_moorim.domain.Post;
import com.politics_moorim.exception.InvalidRequest;
import com.politics_moorim.request.PostCreate;
import com.politics_moorim.request.PostEdit;
import com.politics_moorim.request.PostSearch;
import com.politics_moorim.response.PostResponse;
import com.politics_moorim.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/posts")
@Slf4j
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping("/testcall")
    public String testGet(){
return "Hi, hyeok i'm your first commercial project";
    }

    @PostMapping("")
    public void post(@RequestBody @Valid PostCreate request){
        request.validate();
        postService.write(request);
    }

    // 조회용 API
    // 단건조회 말고 전건 조회
    @GetMapping("")
    public List<PostResponse> getList(@ModelAttribute PostSearch postSearch){
        return postService.getList(postSearch);
    }

    @GetMapping("{postId}")
    public PostResponse get(@PathVariable(name = "postId") Long id) {
        return postService.get(id);
    }

    @PatchMapping("/{postId}")
    public void edit(@PathVariable Long postId, @RequestBody @Valid PostEdit request){
        postService.edit(postId, request);
    }

    @DeleteMapping("/{postId}")
    public void delete(@PathVariable Long postId){
        postService.delete(postId);
    }
}
