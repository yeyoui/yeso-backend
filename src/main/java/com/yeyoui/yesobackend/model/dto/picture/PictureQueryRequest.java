package com.yeyoui.yesobackend.model.dto.picture;

import com.yeyoui.yesobackend.common.PageRequest;
import lombok.Data;

@Data
public class PictureQueryRequest extends PageRequest {
    /**
     * 搜索词
     */
    private String searchText;
}
