package com.imooc.sell4.repository;

import com.imooc.sell4.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {

    @Autowired
    private ProductCategoryRepository repository;

    @Test
    public void testFindOne()
    {
        Optional<ProductCategory> optional=repository.findById(5);
        ProductCategory productCategory=optional.get();
        System.out.println(productCategory.toString());
    }

    @Test
    public void saveTest()
    {
//        ProductCategory productCategory=new ProductCategory("女生最爱",3);
//        ProductCategory result=repository.save(productCategory);
        Optional<ProductCategory> optional=repository.findById(6);
        ProductCategory productCategory=optional.get();
        productCategory.setCategoryType(3);
        ProductCategory result=repository.save(productCategory);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByCategoryTypeListTest()
    {
        List<Integer> list= Arrays.asList(1,2,3);
        List<ProductCategory> result=repository.findByCategoryTypeIn(list);
        Assert.assertEquals(2,result.size());

    }

}