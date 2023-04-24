package com.yeyoui.yesobackend.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yeyoui.yesobackend.DataSource.*;
import com.yeyoui.yesobackend.common.BaseResponse;
import com.yeyoui.yesobackend.common.ErrorCode;
import com.yeyoui.yesobackend.common.ResultUtils;
import com.yeyoui.yesobackend.exception.ThrowUtils;
import com.yeyoui.yesobackend.model.dto.post.PostQueryRequest;
import com.yeyoui.yesobackend.model.dto.search.SearchRequest;
import com.yeyoui.yesobackend.model.dto.user.UserQueryRequest;
import com.yeyoui.yesobackend.model.entity.Picture;
import com.yeyoui.yesobackend.model.enums.SearchTypeEnum;
import com.yeyoui.yesobackend.model.vo.PostVO;
import com.yeyoui.yesobackend.model.vo.SearchVo;
import com.yeyoui.yesobackend.model.vo.UserVO;
import com.yeyoui.yesobackend.service.PictureService;
import com.yeyoui.yesobackend.service.PostService;
import com.yeyoui.yesobackend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 搜索门面
 */
@Component
@Slf4j
public class SearchFacade {
    @Resource
    private UserDatasource userDatasource;
    @Resource
    private PostDatasource postDatasource;
    @Resource
    private PictureDataSource pictureDataSource;

    @Resource
    private DatasourceRegistry datasourceRegistry;

    /**
     * 根据类型搜索对应的内容
     * @param searchRequest
     * @param request
     * @return
     */
    public SearchVo searchAll(@RequestBody SearchRequest searchRequest, HttpServletRequest request) {
        String type = searchRequest.getType();
        SearchTypeEnum searchTypeEnum = SearchTypeEnum.getEnumByValue(type);
//        ThrowUtils.throwIf(StringUtils.isBlank(type), ErrorCode.PARAMS_ERROR);
        String searchText = searchRequest.getSearchText();
        long current = searchRequest.getCurrent();
        long pageSize = searchRequest.getPageSize();

        SearchVo searchVo = new SearchVo();
        if (searchTypeEnum == null) {
            //全搜索
            UserQueryRequest userQueryRequest = new UserQueryRequest();
            userQueryRequest.setUserName(searchText);
            Page<UserVO> userVOPage = userDatasource.doSearch(searchText,current,pageSize);

            PostQueryRequest postQueryRequest = new PostQueryRequest();
            postQueryRequest.setSearchText(searchText);
            Page<PostVO> postVOPage = postDatasource.doSearch(searchText,current,pageSize);

            Page<Picture> picturePage = pictureDataSource.doSearch(searchText, current, pageSize);

            searchVo.setUserList(userVOPage.getRecords());
            searchVo.setPostList(postVOPage.getRecords());
            searchVo.setPictureList(picturePage.getRecords());
        } else {
            DataSource<?> dataSource= datasourceRegistry.getDataSourceByType(type);
            Page<?> page = dataSource.doSearch(searchText, current, pageSize);
            searchVo.setDataList(page.getRecords());
        }
        return searchVo;
    }

}
