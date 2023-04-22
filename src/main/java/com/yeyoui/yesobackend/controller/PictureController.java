package com.yeyoui.yesobackend.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.gson.Gson;
import com.yeyoui.yesobackend.annotation.AuthCheck;
import com.yeyoui.yesobackend.common.BaseResponse;
import com.yeyoui.yesobackend.common.DeleteRequest;
import com.yeyoui.yesobackend.common.ErrorCode;
import com.yeyoui.yesobackend.common.ResultUtils;
import com.yeyoui.yesobackend.constant.UserConstant;
import com.yeyoui.yesobackend.exception.BusinessException;
import com.yeyoui.yesobackend.exception.ThrowUtils;
import com.yeyoui.yesobackend.model.dto.picture.PictureQueryRequest;
import com.yeyoui.yesobackend.model.dto.post.PostAddRequest;
import com.yeyoui.yesobackend.model.dto.post.PostEditRequest;
import com.yeyoui.yesobackend.model.dto.post.PostQueryRequest;
import com.yeyoui.yesobackend.model.dto.post.PostUpdateRequest;
import com.yeyoui.yesobackend.model.entity.Picture;
import com.yeyoui.yesobackend.model.entity.Post;
import com.yeyoui.yesobackend.model.entity.User;
import com.yeyoui.yesobackend.model.vo.PostVO;
import com.yeyoui.yesobackend.service.PictureService;
import com.yeyoui.yesobackend.service.PostService;
import com.yeyoui.yesobackend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 图片请求
 *
 */
@RestController
@RequestMapping("/picture")
@Slf4j
public class PictureController {

    @Resource
    private PictureService pictureService;

    /**
     * 分页获取图片（封装类）
     */
    @PostMapping("/list/page/vo")
    public BaseResponse<Page<Picture>> listPostVOByPage(@RequestBody PictureQueryRequest pictureQueryRequest,
            HttpServletRequest request) {
        long current = pictureQueryRequest.getCurrent();
        long size = pictureQueryRequest.getPageSize();
        String queryText=pictureQueryRequest.getSearchText();
        // 限制爬虫
        ThrowUtils.throwIf(current<0 || size>20, ErrorCode.PARAMS_ERROR);
        Page<Picture> picturePage = pictureService.listPicture(queryText, current, size);
        return ResultUtils.success(picturePage);
    }

}
