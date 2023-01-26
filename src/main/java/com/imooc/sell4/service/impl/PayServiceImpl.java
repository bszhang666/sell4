package com.imooc.sell4.service.impl;

import com.imooc.sell4.dto.OrderDTO;
import com.imooc.sell4.service.PayService;
import com.imooc.sell4.utils.JsonUtil;
import com.imooc.sell4.utils.MathUtil;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PayServiceImpl implements PayService {

    @Autowired
    private BestPayServiceImpl bestPayService;

    @Autowired
    private OrderServiceImpl orderService;

    private  static final String ORDERNAME="weixin order";

    @Override
    public PayResponse create(OrderDTO orderDTO) {
        PayRequest payRequest=new PayRequest();
        payRequest.setOrderId(orderDTO.getOrderId());
        payRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        payRequest.setOrderName(ORDERNAME);
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("微信支付payrequest={}", JsonUtil.toJson(payRequest));
        PayResponse payResponse=bestPayService.pay(payRequest);
        log.info("微信支付response={}",JsonUtil.toJson(payResponse));
        return payResponse;
    }

    @Override
    public PayResponse notify(String notifyData) {
        //异步通知需要验证签名
        //验证支付状态，支付金额以及是否本人
        PayResponse payResponse=bestPayService.asyncNotify(notifyData);

        //修改订单支付状态
        String orderId=payResponse.getOrderId();
        OrderDTO orderDTO=orderService.findOne(orderId);
        if(orderDTO==null)
        {
            log.error("订单不存在");
        }
        if(!MathUtil.equals(payResponse.getOrderAmount(), orderDTO.getOrderAmount().doubleValue()))
        {
            log.error("订单金额不正确");
        }

        orderService.paid(orderDTO);
        return payResponse;
    }
}
