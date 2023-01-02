package com.imooc.sell4.service.impl;

import com.imooc.sell4.dataobject.OrderDetail;
import com.imooc.sell4.dto.OrderDTO;
import com.imooc.sell4.enums.OrderStatusEnum;
import com.imooc.sell4.enums.PayStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Slf4j
@RunWith(SpringRunner.class)
public class OrderServiceImplTest {
    @Autowired
    private OrderServiceImpl orderService;

    private final String buyerOpenId="123";

    @Test
    public void create() {
        OrderDTO orderDTO=new OrderDTO();
        orderDTO.setBuyerName("张博硕");
        orderDTO.setBuyerAddress("吉林大学");
        orderDTO.setBuyerPhone("18844128807");
        orderDTO.setBuyerOpenid(buyerOpenId);
        //购物车
        List<OrderDetail> orderDetailList=new ArrayList<>();
        OrderDetail o1=new OrderDetail();
        o1.setProductId("123456");
        o1.setProductQuantity(2);


        OrderDetail o2=new OrderDetail();
        o2.setProductId("123457");
        o2.setProductQuantity(2);

        orderDetailList.add(o1);
        orderDetailList.add(o2);
        orderDTO.setOrderDetailList(orderDetailList);
        OrderDTO result=orderService.create(orderDTO);
        log.info("创建订单，result={}",result);
        Assert.assertNotNull(result);

    }

    @Test
    public void findOne() {
        OrderDTO orderDTO=orderService.findOne("1672309045781156187");
        log.info("查询单个订单，result={}",orderDTO);
        Assert.assertEquals("1672309045781156187",orderDTO.getOrderId());
    }

    @Test
    public void findList() {
        PageRequest request=new PageRequest(0,2);
        Page<OrderDTO> orderDTOPage=orderService.findList("123",request);
        Assert.assertNotEquals(0,orderDTOPage.getTotalElements());
    }

    @Test
    public void cancel() {
        OrderDTO orderDTO=orderService.findOne("1672322686408253394");
        OrderDTO result=orderService.cancel(orderDTO);
        Assert.assertEquals(OrderStatusEnum.CADEL.getCode(),result.getOrderStatus());
    }

    @Test
    public void finish() {
        OrderDTO orderDTO=orderService.findOne("1672322686408253394");
        OrderDTO result=orderService.finish(orderDTO);
        Assert.assertEquals(OrderStatusEnum.FINISH.getCode(),result.getOrderStatus());
    }

    @Test
    public void paid() {
        OrderDTO orderDTO=orderService.findOne("1672322686408253394");
        OrderDTO result=orderService.paid(orderDTO);
        Assert.assertEquals(PayStatusEnum.SUCCESS.getCode(),result.getOrderStatus());
    }
}