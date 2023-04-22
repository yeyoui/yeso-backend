package com.yeyoui.yesobackend;

import com.yeyoui.yesobackend.config.WxOpenConfig;
import javax.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 主类测试
 *
 * @author <a href="https://github.com/yeyoui">夜悠</a>
 */
@SpringBootTest
class yesoBackendApplicationTests {

    @Resource
    private WxOpenConfig wxOpenConfig;

    @Test
    void contextLoads() {
        System.out.println(wxOpenConfig);
    }

}
