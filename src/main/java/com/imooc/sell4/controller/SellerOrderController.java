package com.imooc.sell4.controller;

import com.imooc.sell4.dto.OrderDTO;
import com.imooc.sell4.enums.ResultEnum;
import com.imooc.sell4.exception.SellException;
import com.imooc.sell4.service.impl.OrderServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/seller/order")
public class SellerOrderController {


    @Autowired
    private OrderServiceImpl orderService;
    /**
     *
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page",defaultValue = "1")Integer page,
                             @RequestParam(value = "size",defaultValue = "10")Integer size,
                             Map<String,Object> map)
    {
        PageRequest pageRequest=new PageRequest(page-1,size);
        Page<OrderDTO> orderDTOPage=orderService.findList(pageRequest);
        map.put("orderDTOPage",orderDTOPage);
        map.put("currentpage",page);
        return new ModelAndView("order/list",map);
    }


    @GetMapping("/cancel")
    public ModelAndView cancel(@RequestParam("orderId")String orderId,
                               Map<String,Object> map)
    {

        try {
            OrderDTO orderDTO=orderService.findOne(orderId);
            orderService.cancel(orderDTO);
        }catch (SellException e) {
            log.error("订单不存在");
            map.put("msg", e.getMessage());
            map.put("url", "list");
            return new ModelAndView("common/error", map);
        }


        map.put("msg", ResultEnum.STOCK_NOT_ENOUGH.getMsg());
        map.put("url", "list");
        return new ModelAndView("common/success");

    }

    @GetMapping("/detail")
    public ModelAndView detail(@RequestParam("orderId")String orderId,
                               Map<String ,Object>map)
    {
        OrderDTO orderDTO=new OrderDTO();
        try {
            orderDTO=orderService.findOne(orderId);

        }catch (SellException e) {
            log.error("订单不存在");
            map.put("msg", e.getMessage());
            map.put("url", "list");
            return new ModelAndView("common/error", map);
        }


        map.put("orderDto",orderDTO);
        return new ModelAndView("order/detail",map);
    }

    @GetMapping("/finish")
    public ModelAndView finished(@RequestParam("orderId")String orderId,
                               Map<String ,Object>map)
    {
        OrderDTO orderDTO=new OrderDTO();
        try {
            orderDTO=orderService.findOne(orderId);
            orderService.finish(orderDTO);
        }catch (SellException e) {
            log.error("订单不存在");
            map.put("msg", e.getMessage());
            map.put("url", "list");
            return new ModelAndView("common/error", map);
        }
        map.put("msg",ResultEnum.DETAIL_NOT_EXIST.getMsg());
        map.put("url","list");
        return new ModelAndView("common/success",map);
    }
}
