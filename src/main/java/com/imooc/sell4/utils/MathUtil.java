package com.imooc.sell4.utils;

public class MathUtil {
    private static final double MONEY_RANGE=0.01;
    /*
    比较两个金额是否相等
     */
    public static boolean equals(double d1,double d2)
    {
        double value=Math.abs(d1-d2);
        if(value<MONEY_RANGE)
        {
            return true;
        }
        return false;
    }
}
