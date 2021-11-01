package com.changgou.file;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author youwei
 * @version 1.0
 * @date 2021/10/22 8:55
 * 封装文件上传信息
 * 时间，author,type,size,附加信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FastDFSFile implements Serializable {
    //文件名字
    private  String name;
    //文件内容
    private byte[] content;
    //文件扩展名
    private String ext;
    //文件MD5摘要值
    private String md5;
    //文件创建者
    private  String author;

    public FastDFSFile(String name, byte[] content, String ext) {
        this.name = name;
        this.content = content;
        this.ext = ext;
    }
}
