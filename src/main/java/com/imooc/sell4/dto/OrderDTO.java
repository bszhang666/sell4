package com.imooc.sell4.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.imooc.sell4.dataobject.OrderDetail;
import com.imooc.sell4.utils.serilaizer.Date2LongSerilaizer;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO {

    private String orderId;

    private String buyerName;

    private String buyerPhone;

    private String buyerAddress;

    private String buyerOpenid;

    private BigDecimal orderAmount;

    private Integer orderStatus;//默认0新下单

    private Integer payStatus;//默认0未支付
    @JsonSerialize(using = Date2LongSerilaizer.class)//时间转化 用新的类复写方法转换
    private Date createTime;
    @JsonSerialize(using = Date2LongSerilaizer.class)
    private Date updateTime;

    List<OrderDetail> orderDetailList;//新建一个DTO对象来存放订单详情信息



}
