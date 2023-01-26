package com.imooc.sell4.controller;

import com.imooc.sell4.dto.OrderDTO;
import com.imooc.sell4.enums.ResultEnum;
import com.imooc.sell4.exception.SellException;
import com.imooc.sell4.service.OrderService;
import com.imooc.sell4.service.PayService;
import com.lly835.bestpay.model.PayResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("/pay")
public class PayController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private PayService payService;

    @GetMapping("/create")
    public ModelAndView create(@RequestParam("orderId")String orderId,
                               @RequestParam("returnUrl")String returnUrl,
                               Map<String,Object> map)
    {
        OrderDTO orderDTO=orderService.findOne(orderId);
        if(orderDTO==null)
        {
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);

        }
        //发起支付
        PayResponse payResponse=payService.create(orderDTO);
        map.put("payResponse",payResponse);
        return new ModelAndView("pay/create",map);
    }


    @PostMapping("/notify")
    public ModelAndView notify(@RequestBody String notifyData)
    {
        payService.notify(notifyData);
        return new ModelAndView("pay/success");
    }

}
