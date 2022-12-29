package com.imooc.sell4.enums;

import lombok.Getter;

@Getter
public enum ResultEnum {
    PRODUCT_NOT_EXIST(0,"订单不存在"),
    STOCK_NOT_ENOUGH(1,"库存不足"),
    ORDER_NOT_EXIST(2,"订单不存在"),
    DETAIL_NOT_EXIST(3,"详情不存在"),
    ORDER_STATUS_ERROR(4,"订单状态不正确"),
    ORDER_UPDATE_ERROR(5,"订单更新失败"),
    ORDER_DELETE_EMPTY(6,"订单详情为空"),
    ;

    private Integer code;

    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
