package com.yeyoui.yesobackend.model.dto.search;

import com.yeyoui.yesobackend.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class SearchRequest extends PageRequest implements Serializable {
    private static final long serialVersionUID = -5353408479249535646L;
    //搜索词
    private String searchText;
    //类型
    private String type;
}
