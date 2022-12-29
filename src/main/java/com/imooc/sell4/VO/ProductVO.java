package com.imooc.sell4.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * 商品信息 包含类目
 */
@Data
public class ProductVO {
    @JsonProperty("name")//返回给前端就是name
    private String categoryName;//类目的名字

    @JsonProperty("type")
    private Integer categoryType;

    @JsonProperty("foods")
    private List<ProductInfoVO> productInfoVOList;

}
