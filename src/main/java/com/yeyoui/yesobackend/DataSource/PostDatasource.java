package com.yeyoui.yesobackend.DataSource;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.gson.Gson;
import com.yeyoui.yesobackend.common.ErrorCode;
import com.yeyoui.yesobackend.constant.CommonConstant;
import com.yeyoui.yesobackend.exception.BusinessException;
import com.yeyoui.yesobackend.exception.ThrowUtils;
import com.yeyoui.yesobackend.mapper.PostFavourMapper;
import com.yeyoui.yesobackend.mapper.PostMapper;
import com.yeyoui.yesobackend.mapper.PostThumbMapper;
import com.yeyoui.yesobackend.model.dto.post.PostEsDTO;
import com.yeyoui.yesobackend.model.dto.post.PostQueryRequest;
import com.yeyoui.yesobackend.model.entity.Post;
import com.yeyoui.yesobackend.model.entity.PostFavour;
import com.yeyoui.yesobackend.model.entity.PostThumb;
import com.yeyoui.yesobackend.model.entity.User;
import com.yeyoui.yesobackend.model.vo.PostVO;
import com.yeyoui.yesobackend.model.vo.UserVO;
import com.yeyoui.yesobackend.service.PostService;
import com.yeyoui.yesobackend.service.UserService;
import com.yeyoui.yesobackend.utils.SqlUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.condition.RequestConditionHolder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 帖子服务实现
 *
 * @author <a href="https://github.com/yeyoui">夜悠</a>
 */
@Service
@Slf4j
public class PostDatasource implements DataSource<PostVO> {

    @Resource
    private PostService postService;

    @Override
    public Page<PostVO> doSearch(String searchText, long pageNum, long pageSize) {
        PostQueryRequest postQueryRequest = new PostQueryRequest();
        postQueryRequest.setSearchText(searchText);
        postQueryRequest.setCurrent(pageNum);
        postQueryRequest.setPageSize(pageSize);
        //获取Request对象
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
        Page<Post> postPage = postService.searchFromEs(postQueryRequest);
        return postService.getPostVOPage(postPage, request);
//        return postService.listPostVoPage(postQueryRequest,request);
    }
}




