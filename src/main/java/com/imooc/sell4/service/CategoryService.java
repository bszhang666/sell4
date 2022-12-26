package com.imooc.sell4.service;

import com.imooc.sell4.dataobject.ProductCategory;

import java.util.List;

public interface CategoryService {
    ProductCategory findOne(Integer categoryId);
    List<ProductCategory> findAll();
    List<ProductCategory> findByCategoryTypeIn(List<Integer> productCategoryTypeList);
    ProductCategory save(ProductCategory productCategory);



}
