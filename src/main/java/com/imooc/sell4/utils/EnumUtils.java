package com.imooc.sell4.utils;

import com.imooc.sell4.enums.CodeEnum;

public class EnumUtils {
    public static <T extends CodeEnum>T getByCode(Integer code, Class<T> enumClass)
    {
        for(T each:enumClass.getEnumConstants())
        {
            if(code.equals(each.getCode()))
            {
                return each;
            }
        }
        return null;
    }
}
