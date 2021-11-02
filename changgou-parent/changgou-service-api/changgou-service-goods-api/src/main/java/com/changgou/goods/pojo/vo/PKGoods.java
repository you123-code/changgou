package com.changgou.goods.pojo.vo;

import com.changgou.goods.pojo.Sku;
import com.changgou.goods.pojo.Spu;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 商品信息组合对象
 * @author youwei
 * @version 1.0
 * @date 2021/11/1 11:56
 */
@Data
public class PKGoods implements Serializable {
    @ApiModelProperty("spu")
    private Spu spu;

    @ApiModelProperty("sku集合")
    private List<Sku> skuList;
}
