package com.politics_moorim.request;

import lombok.Getter;
import lombok.Builder;

import static java.lang.Math.*;

@Getter
@Builder
public class PostSearch {
    private static final int MAX_SIZE = 2000;

    @Builder.Default
    private Integer page = 1;
    @Builder.Default
    private Integer size = 10;

    public long getOffset(){
        return (long) (max(1, page) - 1) * max(size, MAX_SIZE);
    }
}
