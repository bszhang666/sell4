package com.imooc.sell4.service;

import com.imooc.sell4.dto.OrderDTO;

public interface PayService {
    void create(OrderDTO orderDTO);
}
