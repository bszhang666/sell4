package com.imooc.sell4.repository;

import com.imooc.sell4.dataobject.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderMasterRepositoryTest {

    @Autowired
    private OrderMasterRepository repository;

    public final  String OPENID="123";

    @Test
    public void saveTest()
    {
        OrderMaster orderMaster=new OrderMaster();
        orderMaster.setOrderId("1234567");
        orderMaster.setBuyerName("张博硕");
        orderMaster.setBuyerPhone("18844128807");
        orderMaster.setBuyerAddress("吉林大学");
        orderMaster.setBuyerOpenid(OPENID);
        orderMaster.setOrderAmount(new BigDecimal(3));
        OrderMaster result=repository.save(orderMaster);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByBuyerOpenid() {
        Page<OrderMaster> orderMasterPage=repository.findByBuyerOpenid(OPENID,new PageRequest(0,3));
        System.out.println(orderMasterPage.getTotalElements());

    }
}