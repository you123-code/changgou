package com.changgou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author youwei
 * @version 1.0
 * @date 2021/10/8 20:22
 */
@SpringBootApplication
@EnableEurekaServer  //开启Eureka服务
public class EurekaApplication {
    /***
     * 加载启动类，以启动类为当前springBoot的配置标准
     */
    public static void main(String[] args) {
        SpringApplication.run(EurekaApplication.class,args);
    }
}
