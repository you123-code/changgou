package com.changgou;

import com.alibaba.fastjson.JSON;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.changgou.content.feign.ContentFeign;
import com.changgou.content.pojo.Content;
import com.xpand.starter.canal.annotation.CanalEventListener;
import com.xpand.starter.canal.annotation.ListenPoint;
import entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;

/**
 * @author youwei
 * @version 1.0
 * @date 2021/11/12 9:59
 * 实现mysql数据监听
 */
//canal事务监听
@CanalEventListener
public class CanalDataEventListener {
 @Autowired
    private ContentFeign contentFeign;
 @Autowired
    private StringRedisTemplate stringRedisTemplate;
 //自定义数据库操作进行监听
    @ListenPoint(destination = "example",
    schema = "changgou_content",
    table = {"tb_content","tb_content_category"},
    eventType = {
            CanalEntry.EventType.UPDATE,
            CanalEntry.EventType.DELETE,
            CanalEntry.EventType.INSERT,
    })
    public void  onEvenCustomUpdate(CanalEntry.EventType eventType,CanalEntry.RowData rowData){
        //获取列名为category_id的值
         String categoryId = getColumnValue(eventType, rowData);
        //2.调用feign 获取该分类下的所有的广告集合
        Result<List<Content>> categoryResult =  contentFeign.findByCategory(Long.valueOf(categoryId));
        List<Content> data = categoryResult.getData();
        //使用redisTempl存储到redis中
        stringRedisTemplate.boundValueOps("content_"  +  categoryId).set(JSON.toJSONString(data));
    }
    private String getColumnValue(CanalEntry.EventType eventType,CanalEntry.RowData rowData){
        String categoryId = "";
        //判断如果是删除则获取别额forList
        if(eventType == CanalEntry.EventType.DELETE){
            for (CanalEntry.Column column : rowData.getBeforeColumnsList()) {
                if(column.getName().equalsIgnoreCase("category_id")){
                    categoryId = column.getValue();
                    return categoryId;
                }
            }
        }else{
            //判断如果是添加或者是更新，则获取afterList
            for (CanalEntry.Column column : rowData.getAfterColumnsList()) {
                if(column.getName().equalsIgnoreCase("category_id")){
                    categoryId = column.getValue();
                    return categoryId;
                }
            }
        }
        return categoryId;
    }
}
