package com.imooc.sell4.dataobject;

import com.imooc.sell4.enums.OrderStatusEnum;
import com.imooc.sell4.enums.PayStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@DynamicUpdate//和时间相关  时间自动更新
public class OrderMaster {

    @Id
    private String orderId;

    private String buyerName;

    private String buyerPhone;

    private String buyerAddress;

    private String buyerOpenid;

    private BigDecimal orderAmount;

    //默认状态新订单
    private Integer orderStatus= OrderStatusEnum.NEW.getCode();

    private Integer payStatus= PayStatusEnum.WAIT.getCode();

    private Date createTime;

    private Date updateTime;
}
