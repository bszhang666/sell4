package com.imooc.sell4.exception;

import com.imooc.sell4.enums.ResultEnum;
import lombok.Data;

@Data
public class SellException extends RuntimeException{


    private  Integer code;

    public SellException(Integer code,String message) {
        super(message);
        this.code = code;
    }

    public SellException(ResultEnum resultEnum)
    {
        super(resultEnum.getMsg());
        this.code= resultEnum.getCode();
    }
}
