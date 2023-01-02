package com.imooc.sell4.service;

import com.imooc.sell4.dto.OrderDTO;


public interface BuyerService {
    OrderDTO findOrderOne(String openid,String orderId);

    OrderDTO cancelOrder(String openid,String orderId);

}
