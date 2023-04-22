package com.yeyoui.yesobackend.controller;

import com.yeyoui.yesobackend.common.BaseResponse;
import com.yeyoui.yesobackend.common.ErrorCode;
import com.yeyoui.yesobackend.common.ResultUtils;
import com.yeyoui.yesobackend.exception.BusinessException;
import com.yeyoui.yesobackend.model.dto.postthumb.PostThumbAddRequest;
import com.yeyoui.yesobackend.model.entity.User;
import com.yeyoui.yesobackend.service.PostThumbService;
import com.yeyoui.yesobackend.service.UserService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 帖子点赞接口
 *
 * @author <a href="https://github.com/yeyoui">夜悠</a>
 */
@RestController
@RequestMapping("/post_thumb")
@Slf4j
public class PostThumbController {

    @Resource
    private PostThumbService postThumbService;

    @Resource
    private UserService userService;

    /**
     * 点赞 / 取消点赞
     *
     * @param postThumbAddRequest
     * @param request
     * @return resultNum 本次点赞变化数
     */
    @PostMapping("/")
    public BaseResponse<Integer> doThumb(@RequestBody PostThumbAddRequest postThumbAddRequest,
                                         HttpServletRequest request) {
        if (postThumbAddRequest == null || postThumbAddRequest.getPostId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 登录才能点赞
        final User loginUser = userService.getLoginUser(request);
        long postId = postThumbAddRequest.getPostId();
        int result = postThumbService.doPostThumb(postId, loginUser);
        return ResultUtils.success(result);
    }

}
