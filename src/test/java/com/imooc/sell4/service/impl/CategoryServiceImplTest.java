package com.imooc.sell4.service.impl;

import com.imooc.sell4.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceImplTest {

    @Autowired
    private CategoryServiceImpl categoryService;

    @Test
    public void findOne() {
        ProductCategory productCategory=categoryService.findOne(5);
        Assert.assertEquals(new Integer(1),productCategory.getCategoryType());
    }

    @Test
    public void findAll() {
        List<ProductCategory> productCategoryList=categoryService.findAll();
        Assert.assertEquals(3,productCategoryList.size());
    }

    @Test
    public void findByCategoryTypeIn() {
        List<Integer> categoryTypeList= Arrays.asList(1,2,3);
        List<ProductCategory> productCategoryList=categoryService.findByCategoryTypeIn(categoryTypeList);
        Assert.assertEquals(3,productCategoryList.size());
    }

    @Test
    public void save() {
        ProductCategory productCategory=new ProductCategory("热销榜",2);
        ProductCategory result = categoryService.save(productCategory);
        Assert.assertNotNull(result);
    }
}