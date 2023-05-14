package com.politics_moorim.request;


import com.politics_moorim.domain.PostEditor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
public class PostEdit {

    @NotBlank(message = "타이틀을 입력하세요.")
    private String title;
    @NotBlank(message = "콘텐츠를 입력해주세요.")
    private String content;

//    public PostEdit(String title, String content){
//        this.title = title;
//        this.content = content;
//    }
}
