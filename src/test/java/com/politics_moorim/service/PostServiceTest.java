package com.politics_moorim.service;

import com.politics_moorim.domain.Post;
import com.politics_moorim.exception.PostNotFound;
import com.politics_moorim.repository.PostRepository;
import com.politics_moorim.request.PostCreate;
import com.politics_moorim.request.PostEdit;
import com.politics_moorim.request.PostSearch;
import com.politics_moorim.response.PostResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;
    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    void clean(){
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("글 작성")
    void test1(){
        //given
        PostCreate postCreate = PostCreate.builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        // when
        postService.write(postCreate);

        // then
        Assertions.assertEquals(1L, postRepository.count());
        Post post = postRepository.findAll().get(0);
        assertEquals("제목입니다.", post.getTitle());
        assertEquals("내용입니다.", post.getContent());
    }

    @Test
    @DisplayName("글 1개 조회")
    void test2(){
        // given
        Post requestPost = Post.builder()
                .title("foo")
                .content("bar")
                .build();

        postRepository.save(requestPost);

        // when
        PostResponse postResponse = postService.get(requestPost.getId());

        // then
        assertNotNull(postResponse);
        assertEquals("foo", postResponse.getTitle());
        assertEquals("bar", postResponse.getContent());
    }

    @Test
    @DisplayName("글 1 페이지 조회")
    void test3(){
        // given
        List<Post> requestPosts = IntStream.range(1, 30)
                .mapToObj(i -> {
                    return Post.builder()
                            .title("최찬혁 제목 " + i)
                            .content("개발자중에 왕 " + i)
                            .build();
                })
                .collect(Collectors.toList());
        postRepository.saveAll(requestPosts);

//        Pageable pageable = PageRequest.of(0, 5, Sort.Direction.DESC, "id");
        PostSearch postSearch = PostSearch.builder()
                .page(1)
                .build();

        // when
        List<PostResponse> posts = postService.getList(postSearch);

        // then
        assertEquals(10L, posts.size());
        assertEquals("최찬혁 제목 29", posts.get(0).getTitle());
        assertEquals("최찬혁 제목 25", posts.get(4).getTitle());
    }


    @Test
    @DisplayName("글 제목 수정")
    void test4(){
        // given
        Post post = Post.builder()
                .title("개발자중에 왕 최찬혁")
                .content("오늘 하루도 공부할 수 있어 크게 감사합니다.")
                .build();

        postRepository.save(post);
        PostEdit postEdit = PostEdit.builder()
                .title("개발자중에 최강 왕 최찬혁")
                .build();
        // when
        postService.edit(post.getId(), postEdit);

        // then
        Post changedPost = postRepository.findById(post.getId())
                .orElseThrow(() -> new RuntimeException("글이 존재하지 않습니다. id = " + post.getId()));
        Assertions.assertEquals("개발자중에 최강 왕 최찬혁", changedPost.getTitle());
    }
    @Test
    @DisplayName("글 내용 수정")
    void test5(){
        // given
        Post post = Post.builder()
                .title("개발자중에 왕 최찬혁")
                .content("오늘 하루도 공부할 수 있어 크게 감사합니다.")
                .build();

        postRepository.save(post);
        PostEdit postEdit = PostEdit.builder()
                .title("개발자중에 최강 왕 최찬혁")
                .content("오늘 하루도 공부할 수 있어 크게 감사합니다. 지상 최강의 개발자 최찬혁.")
                .build();
        // when
        postService.edit(post.getId(), postEdit);

        // then
        Post changedPost = postRepository.findById(post.getId())
                .orElseThrow(() -> new RuntimeException("글이 존재하지 않습니다. id = " + post.getId()));
        Assertions.assertEquals("개발자중에 최강 왕 최찬혁", changedPost.getTitle());
        Assertions.assertEquals("오늘 하루도 공부할 수 있어 크게 감사합니다. 지상 최강의 개발자 최찬혁.", changedPost.getContent());
    }

//    @Test
//    @DisplayName("글 삭제 테스트")
//    void test6(){
//        // given
//        Post post = Post.builder()
//                .title("개발자중에 왕")
//                .content("최찬혁 입니다.")
//                .build();
//
//        // when
//        postService.delete(post.getId());
//
//        // then
//        Assertions.assertEquals(0, postRepository.count());
//    }

    @Test
    @DisplayName("글 1개 조회")
    void test8(){
        // given
        Post post = Post.builder()
                .title("개발자중에 왕")
                .content("그 이름 최찬혁")
                .build();

        postRepository.save(post);

        // expected
        Assertions.assertThrows(PostNotFound.class, () ->{
            postService.get(post.getId() + 1L);
        });
    }

    @Test
    @DisplayName("글 1개 삭제")
    void test9(){
        // given
        Post post = Post.builder()
                .title("개발자중에 왕")
                .content("그 이름 최찬혁")
                .build();
        postRepository.save(post);

        // expected
        Assertions.assertThrows(PostNotFound.class, () ->{
            postService.delete(post.getId()+1L);
        });
    }

    @Test
    @DisplayName("글 1개 업데이트")
    void test7(){
        // given
        Post post = Post.builder()
                .title("개발자중에 왕")
                .content("그 이름 최찬혁")
                .build();

        postRepository.save(post);

        PostEdit postEdit = PostEdit.builder()
                .title("개발자중에 왕")
                .content("그 이름 최찬혁")
                .build();

        // expected
        Assertions.assertThrows(PostNotFound.class, () ->{
            postService.edit(post.getId() + 1L, postEdit);
        });
    }
}