package com.yeyoui.yesobackend.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yeyoui.yesobackend.model.entity.Picture;

import java.util.List;

/**
 * 搜索图片服务
 */
public interface PictureService {
    Page<Picture> listPicture(String searchText,long pageNum,long pageSize);
}
