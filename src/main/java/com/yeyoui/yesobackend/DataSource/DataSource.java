package com.yeyoui.yesobackend.DataSource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * 搜索数据源接口,新接入的数据源必须实现该接口
 */
public interface DataSource<T> {
    /**
     * 搜索
     * @param searchText
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<T> doSearch(String searchText,long pageNum,long pageSize);
}
