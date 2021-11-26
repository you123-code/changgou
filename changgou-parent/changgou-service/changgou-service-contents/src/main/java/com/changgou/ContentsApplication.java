package com.changgou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import tk.mybatis.spring.annotation.MapperScan;
/**
 * @author youwei
 * @version 1.0
 * @date 2021/11/25 15:38
 */
@SpringBootApplication
@EnableEurekaClient
@MapperScan(basePackages = {"com.changgou.content.dao"})
public class ContentsApplication {
    public static void main(String[] args) {
        SpringApplication.run(ContentsApplication.class);
    }
}
