package com.imooc.sell4.service.impl;

import com.imooc.sell4.dataobject.ProductInfo;
import com.imooc.sell4.dto.CartDTO;
import com.imooc.sell4.enums.ProductStatusEnum;
import com.imooc.sell4.enums.ResultEnum;
import com.imooc.sell4.exception.SellException;
import com.imooc.sell4.repository.ProductInfoRepository;
import com.imooc.sell4.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductInfoRepository repository;

    @Override
    public ProductInfo findOne(String productId) {
        Optional<ProductInfo> optional=repository.findById(productId);
        ProductInfo productInfo=optional.get();
        return productInfo;
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return repository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    @Transactional
    public void increaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO:cartDTOList)
        {
            Optional<ProductInfo> optional=repository.findById(cartDTO.getProductId());
            ProductInfo productInfo=optional.get();
            if(productInfo==null)
            {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            productInfo.setProductStock(productInfo.getProductStock()+ cartDTO.getProductQuantity());
            repository.save(productInfo);
        }
    }

    @Override
    @Transactional//加事务 所有都成功或不成功
    public void decreaseStock(List<CartDTO> cartDTOList) {
        for(CartDTO cartDTO:cartDTOList)
        {
            Optional<ProductInfo> optional=repository.findById(cartDTO.getProductId());
            ProductInfo productInfo=optional.get();
            if(productInfo==null)
            {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            if(productInfo.getProductStock()<cartDTO.getProductQuantity())
            {
                throw new SellException(ResultEnum.STOCK_NOT_ENOUGH);
            }else
            {
                productInfo.setProductStock(productInfo.getProductStock()- cartDTO.getProductQuantity());
                repository.save(productInfo);
            }
        }
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return repository.save(productInfo);
    }
}
