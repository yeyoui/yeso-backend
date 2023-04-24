package com.yeyoui.yesobackend.model.vo;

import com.yeyoui.yesobackend.model.entity.Picture;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 聚合搜索
 */

@Data
public class SearchVo implements Serializable {

    private static final long serialVersionUID = -285978012278439275L;
    private List<UserVO> userList;

    private List<PostVO> postList;

    private List<Picture> pictureList;
    private List<?> dataList;

}
