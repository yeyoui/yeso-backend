package com.yeyoui.yesobackend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yeyoui.yesobackend.model.dto.post.PostQueryRequest;
import com.yeyoui.yesobackend.model.entity.Post;
import com.yeyoui.yesobackend.model.vo.PostVO;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 帖子服务
 *
 * @author <a href="https://github.com/yeyoui">夜悠</a>
 */
public interface PostService extends IService<Post> {

    /**
     * 校验
     *
     * @param post
     * @param add
     */
    void validPost(Post post, boolean add);

    /**
     * 获取查询条件
     *
     * @param postQueryRequest
     * @return
     */
    QueryWrapper<Post> getQueryWrapper(PostQueryRequest postQueryRequest);

    /**
     * 从 ES 查询
     *
     * @param postQueryRequest
     * @return
     */
    Page<Post> searchFromEs(PostQueryRequest postQueryRequest);

    /**
     * 获取帖子封装
     *
     * @param post
     * @param request
     * @return
     */
    PostVO getPostVO(Post post, HttpServletRequest request);

    /**
     * 分页获取帖子封装
     *
     * @param postPage
     * @param request
     * @return
     */
    Page<PostVO> getPostVOPage(Page<Post> postPage, HttpServletRequest request);

    /**
     * 分页获取帖子
     * @param postQueryRequest
     * @return
     */
    Page<PostVO> listPostVoPage(PostQueryRequest postQueryRequest,HttpServletRequest request);

    /**
     * 搜索词建议（标题）
     */
    List<String> getTitleSuggestions(String prefix);
}
