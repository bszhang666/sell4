package com.imooc.sell4.dataobject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.imooc.sell4.enums.ProductStatusEnum;
import com.imooc.sell4.utils.EnumUtils;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Data
@DynamicUpdate
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductInfo {

    @Id
    private String productId;
    private String productName;
    private BigDecimal productPrice;
    private Integer productStock;
    private String productDescription;
    private String productIcon;
    private Integer productStatus;
    private Integer categoryType;
    private Date createTime;
    private Date updateTime;

    @JsonIgnore//转化时候忽略掉
    public ProductStatusEnum getProductStatusEnum()
    {
        return EnumUtils.getByCode(productStatus,ProductStatusEnum.class);
    }



}
