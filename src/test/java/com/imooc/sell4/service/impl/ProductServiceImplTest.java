package com.imooc.sell4.service.impl;

import com.imooc.sell4.dataobject.ProductInfo;
import com.imooc.sell4.repository.ProductInfoRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductServiceImplTest {

    @Autowired
    private ProductInfoRepository repository;

    @Autowired
    private ProductServiceImpl productService;

    @Test
    public void findOne() {
        ProductInfo productInfo=productService.findOne("123456");
        Assert.assertEquals("皮蛋粥",productInfo.getProductName());
    }

    @Test
    public void findUpAll() {
        List<ProductInfo> productInfoList = productService.findUpAll();
        Assert.assertEquals(2,productInfoList.size());
    }

    @Test
    public void findAll() {
        PageRequest pageRequest=new PageRequest(0,2);
        Page<ProductInfo> productInfoPage=productService.findAll(pageRequest);
        System.out.println(productInfoPage.getTotalElements());

    }

    @Test
    public void save() {
        ProductInfo productInfo=new ProductInfo();
        productInfo.setProductId("123457");
        productInfo.setProductName("鸡蛋");
        productInfo.setProductPrice(new BigDecimal(3));
        productInfo.setProductStock(100);
        productInfo.setProductDescription("鸡蛋");
        productInfo.setProductIcon("http://xxxxx,jpg");
        productInfo.setProductStatus(0);
        productInfo.setCategoryType(2);
        ProductInfo result=productService.save(productInfo);
        Assert.assertNotNull(result);
    }
}