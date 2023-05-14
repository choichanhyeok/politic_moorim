package com.politics_moorim.repository;

import com.politics_moorim.domain.Post;
import com.politics_moorim.request.PostSearch;

import java.util.List;

public interface PostRepositoryCustom {
    List<Post> getList(PostSearch postSearch);
}
