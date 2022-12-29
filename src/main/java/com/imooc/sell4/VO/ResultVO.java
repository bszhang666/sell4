package com.imooc.sell4.VO;

import lombok.Data;

/**
 * 返回前端对象 最外层对象
 */
@Data
public class ResultVO<T> {
    private Integer code;

    private String msg;

    private T data;
}
