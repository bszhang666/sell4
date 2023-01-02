package com.imooc.sell4.service.impl;

import com.imooc.sell4.dto.OrderDTO;
import com.imooc.sell4.enums.ResultEnum;
import com.imooc.sell4.exception.SellException;
import com.imooc.sell4.service.BuyerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BuyerServiceImpl implements BuyerService {
    @Autowired
    private OrderServiceImpl orderService;

    @Override
    public OrderDTO findOrderOne(String openid, String orderId) {
        OrderDTO orderDTO=orderService.findOne(orderId);
        if(orderDTO==null)
        {
            return null;
        }
        if(!orderDTO.getBuyerOpenid().equalsIgnoreCase(openid))
        {
            log.error("订单openid不一致");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        return orderDTO;

    }

    @Override
    public OrderDTO cancelOrder(String openid, String orderId) {
        OrderDTO orderDTO=findOrderOne(openid,orderId);

        if(orderDTO==null)
        {
            log.error("订单不存在");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        OrderDTO result=orderService.cancel(orderDTO);
        return result;
    }
}
