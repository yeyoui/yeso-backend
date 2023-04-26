package com.yeyoui.yesobackend.esdao;

import cn.hutool.json.JSONUtil;
import com.yeyoui.yesobackend.model.dto.post.PostEsDTO;
import com.yeyoui.yesobackend.model.dto.post.PostQueryRequest;
import com.yeyoui.yesobackend.model.entity.Post;
import com.yeyoui.yesobackend.service.PostService;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import javax.annotation.Resource;

import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.SuggestionBuilder;
import org.elasticsearch.search.suggest.completion.CompletionSuggestion;
import org.elasticsearch.search.suggest.completion.CompletionSuggestionBuilder;
import org.elasticsearch.xcontent.XContentType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.BulkOptions;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;

/**
 * 帖子 ES 操作测试
 *
 * @author <a href="https://github.com/yeyoui">夜悠</a>
 */
@SpringBootTest
public class PostEsDaoTest {

    @Resource
    private PostEsDao postEsDao;

    @Resource
    private PostService postService;
    @Resource
    private ElasticsearchRestTemplate elasticsearchRestTemplate;
    @Resource
    private RestHighLevelClient elasticSearchClient;

    @Test
    void test() {
        PostQueryRequest postQueryRequest = new PostQueryRequest();
        com.baomidou.mybatisplus.extension.plugins.pagination.Page<Post> page =
                postService.searchFromEs(postQueryRequest);
        System.out.println(page);
    }

    @Test
    void testAdd(){
        PostEsDTO postEsDTO = new PostEsDTO();
        postEsDTO.setId(2L);
        postEsDTO.setTitle("卷啊卷啊");
        postEsDTO.setContent("计算机卷麻了呀.....");
        postEsDTO.setTags(Arrays.asList("Java", "实习"));
        postEsDTO.setUserId(1L);
        postEsDTO.setCreateTime(new Date());
        postEsDTO.setUpdateTime(new Date());
        postEsDTO.setIsDelete(0);
        postEsDao.save(postEsDTO);
        System.out.println(postEsDTO);
    }

    @Test
    void testFindByTitle(){
        List<PostEsDTO> postEsDTOS = postEsDao.findByTitle("卷啊卷啊");
        System.out.println(postEsDTOS.get(0));

    }
    @Test
    void testSelect() {
        System.out.println(postEsDao.count());
        Page<PostEsDTO> postPage = postEsDao.findAll(
                PageRequest.of(0, 5, Sort.by("createTime")));
        List<PostEsDTO> postList = postPage.getContent();
        System.out.println(postList);
    }

    @Test
    void testAdd2() {
        PostEsDTO postEsDTO = new PostEsDTO();
        postEsDTO.setId(1L);
        postEsDTO.setTitle("test");
        postEsDTO.setContent("test");
        postEsDTO.setTags(Arrays.asList("java", "python"));
//        postEsDTO.setThumbNum(1);
//        postEsDTO.setFavourNum(1);
        postEsDTO.setUserId(1L);
//        postEsDTO.setCreateTime(new Date());
//        postEsDTO.setUpdateTime(new Date());
        postEsDTO.setIsDelete(0);
        postEsDao.save(postEsDTO);
        System.out.println(postEsDTO.getId());
    }

    @Test
    void syncDataToEs() {
        List<Post> postList = postService.list();
        List<PostEsDTO> postEsDTOS = postList.stream().map(PostEsDTO::objToDto).collect(Collectors.toList());
        List<IndexQuery> indexQueries = new ArrayList<>();
        for (PostEsDTO postEsDTO : postEsDTOS) {
            IndexQuery indexQuery = new IndexQuery();
            indexQuery.setId(String.valueOf(postEsDTO.getId()));
            indexQuery.setSource(JSONUtil.toJsonStr(postEsDTO));
            // 如果需要设置文档类型，可以使用 setType() 方法
            indexQueries.add(indexQuery);
        }
        elasticsearchRestTemplate.bulkIndex(indexQueries, PostEsDTO.class);
    }

    @Test
    void testSuggestion() throws IOException {
        SearchRequest searchRequest = new SearchRequest("post");
        searchRequest.source()
                .suggest(new SuggestBuilder().addSuggestion(
                        "titleSuggesion",
                        SuggestBuilders.completionSuggestion("suggestion")
                                .prefix("大三")
                                .size(10)
                ));
        SearchResponse response = elasticSearchClient.search(searchRequest, RequestOptions.DEFAULT);

        Suggest suggest = response.getSuggest();
        CompletionSuggestion suggestion=suggest.getSuggestion("titleSuggesion");
        List<CompletionSuggestion.Entry.Option> options = suggestion.getOptions();
        List<String> searchSuggestion=new ArrayList<>();
        for (CompletionSuggestion.Entry.Option option : options) {
            String s = option.getText().toString();
            searchSuggestion.add(s);
        }
    }

    @Test
    void testFindById() {
        Optional<PostEsDTO> postEsDTO = postEsDao.findById(1L);
        System.out.println(postEsDTO);
    }

    @Test
    void testCount() {
        System.out.println(postEsDao.count());
    }

    @Test
    void testFindByCategory() {
        List<PostEsDTO> postEsDaoTestList = postEsDao.findByUserId(1L);
        System.out.println(postEsDaoTestList);
    }
}
