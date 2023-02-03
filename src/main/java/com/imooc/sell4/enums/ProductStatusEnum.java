package com.imooc.sell4.enums;

import lombok.Getter;

/**
 * 商品状态
 */

@Getter
public enum ProductStatusEnum implements CodeEnum{
    UP(0,"上架"),//上架
    DOWN(1,"下架")//下架
    ;
    private Integer code;

    private String message;

    ProductStatusEnum(Integer code) {
        this.code = code;
    }

    ProductStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    ProductStatusEnum() {
    }
}
