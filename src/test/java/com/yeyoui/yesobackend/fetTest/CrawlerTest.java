package com.yeyoui.yesobackend.fetTest;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.yeyoui.yesobackend.model.entity.Picture;
import com.yeyoui.yesobackend.model.entity.Post;
import com.yeyoui.yesobackend.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

//@SpringBootTest
@Slf4j
public class CrawlerTest {

    @Resource
    private PostService postService;

    @Test
    void testFetchPicture() throws IOException {
        int current=1;
        String url = "https://www.bing.com/images/search?q=%E6%B7%B1%E6%B5%B7%E7%94%B5%E5%BD%B1&qs=n&fo" +
                "rm=QBIR&sp=-1&lq=0&pq=%E6%B7%B1%E6%B5%B7%E7%94%B5%E5%BD%B1&sc=10-4&cvid=A5D55D3FE539412DBF46FAE0A14E49" +
                "0B&ghsh=0&ghacc=0&first="+current;
        Document doc = Jsoup.connect(url).get();
        Elements elements = doc.select(".iuscp.isv");
        ArrayList<Picture> pictures = new ArrayList<>();
        for (Element element : elements) {
            //获取图片地址
            String m = element.select(".iusc").get(0).attr("m");
            Map<String, Object> map = JSONUtil.toBean(m, Map.class);
            String murl=(String)map.get("murl");
            String title = element.select(".inflnk").get(0).attr("aria-label");
            pictures.add(new Picture(title, murl));
        }
    }

    @Test
    void testFetchPassage(){
        //1.获取信息
        String url="https://www.code-nav.cn/api/post/list/page/vo";
        String json="{\"sortField\":\"createTime\",\"sortOrder\":\"descend\",\"reviewStatus\":1,\"current\":1}";
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
            post.setCreateTime(new Date());
            post.setUpdateTime(new Date());

            postList.add(post);
        }
        //3数据入库
        boolean saveBatch = postService.saveBatch(postList);
        Assertions.assertTrue(saveBatch);
    }
}
