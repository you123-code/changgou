package com.changgou.content.feign;

import com.changgou.content.pojo.Content;
import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author youwei
 * @version 1.0
 * @date 2021/11/26 9:18
 */
@FeignClient(name="content")
@RequestMapping(value = "/content")
public interface ContentFeign {
    /***
     * 根据分类ID查询所有广告
     */
    @GetMapping(value = "/list/category")
    Result<List<Content>> findByCategory(Long id);
    }
