package com.imooc.sell4.controller;

import com.imooc.sell4.VO.ResultVO;
import com.imooc.sell4.converter.OrderForm2OrderDTOConverter;
import com.imooc.sell4.dto.OrderDTO;
import com.imooc.sell4.enums.ResultEnum;
import com.imooc.sell4.exception.SellException;
import com.imooc.sell4.form.OrderForm;
import com.imooc.sell4.service.impl.BuyerServiceImpl;
import com.imooc.sell4.service.impl.OrderServiceImpl;
import com.imooc.sell4.service.impl.ProductServiceImpl;
import com.imooc.sell4.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private OrderServiceImpl orderService;

    @Autowired
    private BuyerServiceImpl buyerService;


    @PostMapping("/create")
    public ResultVO<Map<String,String>> create(@Valid OrderForm orderForm, BindingResult bindingResult)
    {
        if(bindingResult.hasErrors())
        {
            log.error("创建订单，参数不正确  orderForm={}",orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),bindingResult.getFieldError().getDefaultMessage());
        }
        OrderDTO orderDTO=OrderForm2OrderDTOConverter.convert(orderForm);
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList()))
        {
            log.error("购物车为空");
            throw new SellException(ResultEnum.CAET_EMPTY);
        }
        OrderDTO result=orderService.create(orderDTO);
        Map<String ,String >map=new HashMap<>();
        map.put("orderId", result.getOrderId());
        return ResultVOUtil.success(map);
    }

    @GetMapping("/list")
    public ResultVO<List<OrderDTO>> list(@RequestParam("openid")String openid,
                                         @RequestParam(value = "page",defaultValue = "0")Integer page,
                                         @RequestParam(value = "size",defaultValue = "10")Integer size)
    {
        if(StringUtils.isEmpty(openid))
        {
            log.error("openid为空");
            throw new SellException(ResultEnum.OPENID_EMPTY);
        }
        PageRequest pageRequest=new PageRequest(page,size);
        Page<OrderDTO> orderDTOPage=orderService.findList(openid,pageRequest);
        return ResultVOUtil.success(orderDTOPage.getContent());//用getContent获取page的内容
    }

    @GetMapping("/detail")
    public ResultVO<OrderDTO> detail(@RequestParam("openid")String openid,
                                     @RequestParam("orderId")String orderId)
    {
        if(StringUtils.isEmpty(openid))
        {
            log.error("openid为空");
            throw new SellException(ResultEnum.OPENID_EMPTY);
        }
        if(StringUtils.isEmpty(orderId))
        {
            log.error("orderId为空");
            throw new SellException(ResultEnum.OPENID_EMPTY);
        }

        OrderDTO orderDTO=buyerService.findOrderOne(openid, orderId);
        return ResultVOUtil.success(orderDTO);
    }

    @PostMapping("/cancel")
    public ResultVO cancel(@RequestParam("openid")String openid,
                             @RequestParam("orderId")String orderId)
    {
        OrderDTO orderDTO=buyerService.cancelOrder(openid, orderId);

        return ResultVOUtil.success();
    }
}
