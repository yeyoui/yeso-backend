package com.yeyoui.yesobackend.common;

import java.io.Serializable;
import lombok.Data;

/**
 * 删除请求
 *
 * @author <a href="https://github.com/yeyoui">夜悠</a>
 */
@Data
public class DeleteRequest implements Serializable {

    /**
     * id
     */
    private Long id;

    private static final long serialVersionUID = 1L;
}
