package com.imooc.sell4.service;

import com.imooc.sell4.dataobject.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    ProductInfo findOne(String productId);

    List<ProductInfo> findUpAll();//上架的所有

    Page<ProductInfo> findAll(Pageable pageable);//分页

    //加库存

    //减库存

    ProductInfo save(ProductInfo productInfo);
}
