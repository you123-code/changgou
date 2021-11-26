package com.changgou;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.xpand.starter.canal.annotation.*;

/**
 * @author youwei
 * @version 1.0
 * @date 2021/11/12 9:59
 * 实现mysql数据监听
 */
//canal事务监听
@CanalEventListener
public class CanalDataEventListener {
    /**
     * 增加监听，只有增加之后的数据，
     * @param eventType:当前操作的类型，增加数据
     */
    @InsertListenPoint
    public  void onEventInsert(CanalEntry.EventType eventType, CanalEntry.RowData rowData){
        for (CanalEntry.Column column : rowData.getAfterColumnsList()) {
            System.out.println("列名：" + column.getName() + "-----------变更的数据："+column.getValue());
        }
    }
    /**
     * 修改监听
     */
    @UpdateListenPoint
    public  void onEventUpdate(CanalEntry.EventType eventType, CanalEntry.RowData rowData){
        for (CanalEntry.Column column : rowData.getBeforeColumnsList()) {
            System.out.println("修改前：列名：" + column.getName() + "-----------变更的数据："+column.getValue());
        }
        for (CanalEntry.Column column : rowData.getAfterColumnsList()) {
            System.out.println("修改后：列名：" + column.getName() + "-----------变更的数据："+column.getValue());
        }
    }

    /**
     * 删除监听
     */
    @DeleteListenPoint
    public  void onEventDelete(CanalEntry.EventType eventType, CanalEntry.RowData rowData){
        for (CanalEntry.Column column : rowData.getBeforeColumnsList()) {
            System.out.println("删除前：列名：" + column.getName() + "-----------变更的数据："+column.getValue());
        }
    }

    /**
     * 自定义监听
     */
    @ListenPoint(eventType =  {
            CanalEntry.EventType.DELETE, CanalEntry.EventType.UPDATE},
            schema = {"changgou_content"},
            table = {"tb_content"},
            destination = "example"
        )
    public  void onEventCustomUpdate(CanalEntry.EventType eventType, CanalEntry.RowData rowData){
        for (CanalEntry.Column column : rowData.getBeforeColumnsList()) {
            System.out.println("自定义操作前：列名：" + column.getName() + "-----------变更的数据："+column.getValue());
        }
        for (CanalEntry.Column column : rowData.getAfterColumnsList()) {
            System.out.println("自定义操作后：列名：" + column.getName() + "-----------变更的数据："+column.getValue());
        }
    }
}
