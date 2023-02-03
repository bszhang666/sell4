package com.imooc.sell4.service;

import com.imooc.sell4.dataobject.ProductInfo;
import com.imooc.sell4.dto.CartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    ProductInfo findOne(String productId);

    List<ProductInfo> findUpAll();//上架的所有

    Page<ProductInfo> findAll(Pageable pageable);//分页

    //加库存
    void increaseStock(List<CartDTO> cartDTOList);
    //减库存
    void decreaseStock(List<CartDTO> cartDTOList);
    ProductInfo save(ProductInfo productInfo);

    ProductInfo  offSale(String productId);

    ProductInfo onSale(String productId);
}
