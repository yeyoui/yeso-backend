package com.yeyoui.yesobackend.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yeyoui.yesobackend.common.BaseResponse;
import com.yeyoui.yesobackend.common.ResultUtils;
import com.yeyoui.yesobackend.model.dto.post.PostQueryRequest;
import com.yeyoui.yesobackend.model.dto.search.SearchRequest;
import com.yeyoui.yesobackend.model.dto.user.UserQueryRequest;
import com.yeyoui.yesobackend.model.entity.Picture;
import com.yeyoui.yesobackend.model.vo.PostVO;
import com.yeyoui.yesobackend.model.vo.SearchVo;
import com.yeyoui.yesobackend.model.vo.UserVO;
import com.yeyoui.yesobackend.service.PictureService;
import com.yeyoui.yesobackend.service.PostService;
import com.yeyoui.yesobackend.service.UserService;
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
    private UserService userService;
    @Resource
    private PostService postService;
    @Resource
    private PictureService pictureService;

    @PostMapping("/all")
    public BaseResponse<SearchVo> searchAll(@RequestBody SearchRequest searchRequest, HttpServletRequest request){
        String searchText = searchRequest.getSearchText();
        UserQueryRequest userQueryRequest = new UserQueryRequest();
        userQueryRequest.setUserName(searchText);
        Page<UserVO> userVOPage = userService.listUserVoPage(userQueryRequest);

        PostQueryRequest postQueryRequest = new PostQueryRequest();
        postQueryRequest.setSearchText(searchText);
        Page<PostVO> postVOPage = postService.listPostVoPage(postQueryRequest, request);

        Page<Picture> picturePage = pictureService.listPicture(searchText, 1, 10);

        SearchVo searchVo = new SearchVo();
        searchVo.setUserList(userVOPage.getRecords());
        searchVo.setPostList(postVOPage.getRecords());
        searchVo.setPictureList(picturePage.getRecords());

        return ResultUtils.success(searchVo);
    }

}
