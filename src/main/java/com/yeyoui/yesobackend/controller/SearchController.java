package com.yeyoui.yesobackend.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yeyoui.yesobackend.common.BaseResponse;
import com.yeyoui.yesobackend.common.ErrorCode;
import com.yeyoui.yesobackend.common.ResultUtils;
import com.yeyoui.yesobackend.exception.ThrowUtils;
import com.yeyoui.yesobackend.manager.SearchFacade;
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
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.ResponseUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/search")
public class SearchController {
    @Resource
    private SearchFacade searchFacade;

    @PostMapping("/all")
    public BaseResponse<SearchVo> searchAll(@RequestBody SearchRequest searchRequest, HttpServletRequest request){
        return ResultUtils.success(searchFacade.searchAll(searchRequest, request));
    }

}
