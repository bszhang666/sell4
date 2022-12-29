package com.imooc.sell4.utils;

import java.util.Random;

public class KeyUtils {
    public static synchronized String genUniqueKey()//生成唯一主键,时间+随机数
    {
        Random random=new Random();
        System.currentTimeMillis();
        Integer number=random.nextInt(900000)+100000;
        return System.currentTimeMillis()+String.valueOf(number);
    }
}
