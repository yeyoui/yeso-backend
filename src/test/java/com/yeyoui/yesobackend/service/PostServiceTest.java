package com.yeyoui.yesobackend.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yeyoui.yesobackend.model.dto.post.PostQueryRequest;
import com.yeyoui.yesobackend.model.entity.Post;

import javax.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 帖子服务测试
 *
 * @author <a href="https://github.com/yeyoui">夜悠</a>
 */
@SpringBootTest
class PostServiceTest {

    @Resource
    private PostService postService;

    @Test
    void searchFromEs() {
        PostQueryRequest postQueryRequest = new PostQueryRequest();
        postQueryRequest.setUserId(1L);
        Page<Post> postPage = postService.searchFromEs(postQueryRequest);
        Assertions.assertNotNull(postPage);
    }

}
