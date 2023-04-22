package com.yeyoui.yesobackend;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 主类（项目启动入口）
 *
 * @author <a href="https://github.com/yeyoui">夜悠</a>
 */
// todo 如需开启 Redis，须移除 exclude 中的内容
@SpringBootApplication(exclude = {RedisAutoConfiguration.class})
@MapperScan("com.yeyoui.yesobackend.mapper")
@EnableScheduling
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
public class yesoBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(yesoBackendApplication.class, args);
    }

}
