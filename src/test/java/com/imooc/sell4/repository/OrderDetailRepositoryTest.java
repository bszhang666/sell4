package com.imooc.sell4.repository;

import com.imooc.sell4.dataobject.OrderDetail;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class OrderDetailRepositoryTest {

    @Autowired
    private OrderDetailRepository repository;



    @Test
    public void findByOrderId() {
        List<OrderDetail> orderDetailList= repository.findByOrderId("1234567");
        Assert.assertEquals(2,orderDetailList.size());

    }

    @Test
    public void saveTest()
    {
        OrderDetail orderDetail=new OrderDetail();
        orderDetail.setDetailId("123456789");
        orderDetail.setOrderId("1234567");
        orderDetail.setProductIcon("http://xxxxx.jpg");
        orderDetail.setProductId("123457");
        orderDetail.setProductName("鸡蛋");
        orderDetail.setProductPrice(new BigDecimal(9));
        orderDetail.setProductQuantity(3);

        OrderDetail result=repository.save(orderDetail);
        Assert.assertNotNull(result);
    }
}