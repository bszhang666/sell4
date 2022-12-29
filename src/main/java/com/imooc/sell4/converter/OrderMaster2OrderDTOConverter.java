package com.imooc.sell4.converter;

import com.imooc.sell4.dataobject.OrderMaster;
import com.imooc.sell4.dto.OrderDTO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMaster2OrderDTOConverter {
    public static OrderDTO convert(OrderMaster orderMaster)
    {
        OrderDTO orderDTO=new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        return orderDTO;
    }

    public static List<OrderDTO> convert(List<OrderMaster> orderMasterList)
    {
        return orderMasterList.stream().map(item->convert(item)).collect(Collectors.toList());

    }
}
