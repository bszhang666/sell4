package com.imooc.sell4.service.impl;

import com.imooc.sell4.converter.OrderMaster2OrderDTOConverter;
import com.imooc.sell4.dataobject.OrderDetail;
import com.imooc.sell4.dataobject.OrderMaster;
import com.imooc.sell4.dataobject.ProductInfo;
import com.imooc.sell4.dto.CartDTO;
import com.imooc.sell4.dto.OrderDTO;
import com.imooc.sell4.enums.OrderStatusEnum;
import com.imooc.sell4.enums.PayStatusEnum;
import com.imooc.sell4.enums.ResultEnum;
import com.imooc.sell4.exception.SellException;
import com.imooc.sell4.repository.OrderDetailRepository;
import com.imooc.sell4.repository.OrderMasterRepository;
import com.imooc.sell4.service.OrderService;
import com.imooc.sell4.utils.KeyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProductServiceImpl productService;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    String orderId=KeyUtils.genUniqueKey();

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) {
        List<OrderDetail> orderDetailList=orderDTO.getOrderDetailList();
        BigDecimal orderAmount=new BigDecimal(BigInteger.ZERO);
        List<CartDTO> cartDTOList=new ArrayList<>();

        for (OrderDetail orderDetail:orderDetailList)
        {
            ProductInfo productInfo=productService.findOne(orderDetail.getProductId());
            if(productInfo==null)
            {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            orderAmount=productInfo.getProductPrice().multiply(new BigDecimal(orderDetail.getProductQuantity())).add(orderAmount);

            BeanUtils.copyProperties(productInfo,orderDetail);
            orderDetail.setOrderId(orderId);
            orderDetail.setDetailId(KeyUtils.genUniqueKey());
            orderDetailRepository.save(orderDetail);

        }
        OrderMaster orderMaster=new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO,orderMaster);

        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMasterRepository.save(orderMaster);

        cartDTOList=orderDetailList.stream().map(item->
                new CartDTO(item.getProductId(), item.getProductQuantity())).collect(Collectors.toList());

        productService.decreaseStock(cartDTOList);

        return orderDTO;


    }

    @Override
    public OrderDTO findOne(String orderId) {
        Optional<OrderMaster> optional=orderMasterRepository.findById(orderId);
        OrderMaster orderMaster=optional.get();
        if(orderMaster==null)
        {
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        List<OrderDetail> orderDetailList=orderDetailRepository.findByOrderId(orderId);
        if(orderDetailList==null)
        {
            throw new SellException(ResultEnum.DETAIL_NOT_EXIST);
        }
        OrderDTO orderDTO=new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);

        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {

        Page<OrderMaster> orderMasterPage=orderMasterRepository.findByBuyerOpenid(buyerOpenid,pageable);
        List<OrderDTO> orderDTOList=OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());
        Page<OrderDTO> orderDTOPage=new PageImpl<>(orderDTOList,pageable,orderMasterPage.getTotalElements());
        return  orderDTOPage;
    }

    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {
        OrderMaster orderMaster=new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        //判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode()))
        {
            log.error("取消订单，订单状态不正确,orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //修改订单状态
        orderMaster.setOrderStatus(OrderStatusEnum.CADEL.getCode());
        orderDTO.setOrderStatus(OrderStatusEnum.CADEL.getCode());
        OrderMaster result=orderMasterRepository.save(orderMaster);
        if(result==null)
        {
            log.error("订单更新失败");
            throw new SellException(ResultEnum.ORDER_UPDATE_ERROR);
        }
        //返回库存
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList()))
        {
            log.error("取消订单，订单中无商品详情,orderDto={}",orderDTO);
            throw new SellException(ResultEnum.ORDER_DELETE_EMPTY);
        }
        List<CartDTO> cartDTOList=orderDTO.getOrderDetailList().stream().map(item->new CartDTO(item.getProductId(), item.getProductQuantity())).collect(Collectors.toList());
        productService.increaseStock(cartDTOList);
        //如果已支付。需要退款
        if(orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS))
        {
            //TODO
        }
            return orderDTO;

    }

    @Override
    public OrderDTO finish(OrderDTO orderDTO) {
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode()))
        {
            log.error("订单状态不正确");
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        orderDTO.setOrderStatus(OrderStatusEnum.FINISH.getCode());
        OrderMaster orderMaster=new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster result=orderMasterRepository.save(orderMaster);
        if(result==null)
        {
            log.error("订单完结失败");
            throw new SellException(ResultEnum.ORDER_UPDATE_ERROR);
        }
        return orderDTO;

    }

    @Override
    public OrderDTO paid(OrderDTO orderDTO) {
        if(orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS))
        {
            log.error("订单支付状态不正确");
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMaster orderMaster=new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster result=orderMasterRepository.save(orderMaster);
        if(result==null)
        {
            log.error("订单支付失败");
            throw new SellException(ResultEnum.ORDER_UPDATE_ERROR);
        }
        return orderDTO;
    }
}
