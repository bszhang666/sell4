package com.imooc.sell4.enums;

import lombok.Getter;

@Getter
public enum OrderStatusEnum {
    NEW(0,"新订单"),
    FINISH(1,"完结"),
    CADEL(2,"取消"),
    ;
    private Integer code;

    private String msg;

    OrderStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    OrderStatusEnum() {
    }
}
