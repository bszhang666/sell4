package com.imooc.sell4.service.impl;

import com.imooc.sell4.dataobject.ProductCategory;
import com.imooc.sell4.repository.ProductCategoryRepository;
import com.imooc.sell4.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    @Override
    public ProductCategory findOne(Integer categoryId) {
        Optional<ProductCategory>optional =productCategoryRepository.findById(categoryId);
        ProductCategory productCategory=optional.get();
        return productCategory;
    }

    @Override
    public List<ProductCategory> findAll() {
        return productCategoryRepository.findAll();
    }

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> productCategoryTypeList) {
        return productCategoryRepository.findByCategoryTypeIn(productCategoryTypeList);
    }

    @Override
    public ProductCategory save(ProductCategory productCategory) {
        return productCategoryRepository.save(productCategory);
    }
}
