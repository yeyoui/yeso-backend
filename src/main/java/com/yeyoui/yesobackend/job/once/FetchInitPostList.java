package com.yeyoui.yesobackend.job.once;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.yeyoui.yesobackend.esdao.PostEsDao;
import com.yeyoui.yesobackend.model.dto.post.PostEsDTO;
import com.yeyoui.yesobackend.model.entity.Post;
import com.yeyoui.yesobackend.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 爬取文章数据
 */
//@Component
@Slf4j
public class FetchInitPostList implements CommandLineRunner {

    @Resource
    private PostService postService;
    @Override
    public void run(String... args) throws Exception {
        //1.获取信息
        String url="https://www.code-nav.cn/api/post/list/page/vo";
        String json="{\"sortField\":\"createTime\",\"sortOrder\":\"descend\",\"reviewStatus\":1,\"current\":2}";
        String result = HttpRequest.post(url)
                .body(json)
                .execute().body();
        //2.json转对象
        Map<String, Object> map = JSONUtil.toBean(result, Map.class);
        JSONObject data = (JSONObject) map.get("data");
        JSONArray records =(JSONArray) data.get("records");
        List<Post> postList = new ArrayList<>();
        for (Object record : records) {
            JSONObject tempRecord=(JSONObject) record;
            //提取数据后封装
            Post post = new Post();
            post.setTitle(tempRecord.getStr("title"));
            post.setContent(tempRecord.getStr("content"));
            JSONArray tagsArr=(JSONArray) tempRecord.get("tags");
            String tagsStr = JSONUtil.toJsonStr(tagsArr);
            post.setTags(tagsStr);
            post.setUserId(1L);
//            post.setCreateTime(new Date());
//            post.setUpdateTime(new Date());

            postList.add(post);
        }
        //3数据入库
        boolean saveSuccessfully = postService.saveBatch(postList);
        if(saveSuccessfully){
            log.info("获取初始化帖子成功，条数={}",postList.size());
        }
    }
}
