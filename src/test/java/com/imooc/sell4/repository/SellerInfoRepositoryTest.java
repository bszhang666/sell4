package com.imooc.sell4.repository;

import com.imooc.sell4.dataobject.SellerInfo;
import com.imooc.sell4.utils.KeyUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SellerInfoRepositoryTest {

    @Autowired
    private SellerInfoRepository repository;

    @Test
    public void save()
    {
        SellerInfo sellerInfo=new SellerInfo();
        sellerInfo.setSellerId(KeyUtils.genUniqueKey());
        sellerInfo.setUsername("admin");
        sellerInfo.setPassword("admin");
        sellerInfo.setOpenid("abc");
        SellerInfo result=repository.save(sellerInfo);
        Assert.assertNotNull(result);

    }

    @Test
    public void findByOpenid() {
        SellerInfo result=repository.findByOpenid("abc");
        Assert.assertEquals(result.getOpenid(),"abc");
    }
}