package com.imooc.sell4.service.impl;

import com.imooc.sell4.dto.OrderDTO;
import com.imooc.sell4.service.PayService;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class PayServiceImpl implements PayService {
    @Override
    public void create(OrderDTO orderDTO) {
        BestPayServiceImpl bestPayService=new BestPayServiceImpl();
        bestPayService.setWxPayH5Config();
    }
}
