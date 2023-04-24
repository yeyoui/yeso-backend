package com.yeyoui.yesobackend.DataSource;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeyoui.yesobackend.common.ErrorCode;
import com.yeyoui.yesobackend.constant.CommonConstant;
import com.yeyoui.yesobackend.constant.UserConstant;
import com.yeyoui.yesobackend.exception.BusinessException;
import com.yeyoui.yesobackend.mapper.UserMapper;
import com.yeyoui.yesobackend.model.dto.user.UserQueryRequest;
import com.yeyoui.yesobackend.model.entity.User;
import com.yeyoui.yesobackend.model.enums.UserRoleEnum;
import com.yeyoui.yesobackend.model.vo.LoginUserVO;
import com.yeyoui.yesobackend.model.vo.PostVO;
import com.yeyoui.yesobackend.model.vo.UserVO;
import com.yeyoui.yesobackend.service.UserService;
import com.yeyoui.yesobackend.utils.SqlUtils;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户服务实现
 *
 * @author <a href="https://github.com/yeyoui">夜悠</a>
 */
@Service
@Slf4j
public class UserDatasource  implements DataSource<UserVO> {

    @Resource
    private UserService userService;

    @Override
    public Page<UserVO> doSearch(String searchText, long pageNum, long pageSize) {
        UserQueryRequest userQueryRequest = new UserQueryRequest();
        userQueryRequest.setUserName(searchText);
        userQueryRequest.setCurrent(pageNum);
        userQueryRequest.setPageSize(pageSize);
        return userService.listUserVoPage(userQueryRequest);
    }
}
