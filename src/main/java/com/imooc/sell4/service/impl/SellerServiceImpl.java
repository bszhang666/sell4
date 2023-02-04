package com.imooc.sell4.service.impl;

import com.imooc.sell4.dataobject.SellerInfo;
import com.imooc.sell4.repository.SellerInfoRepository;
import com.imooc.sell4.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerServiceImpl implements SellerService {
    @Autowired
    private SellerInfoRepository sellerInfoRepository;

    @Override
    public SellerInfo findSellerInfoByOpenid(String openid) {
        SellerInfo sellerInfo=sellerInfoRepository.findByOpenid(openid);
        return sellerInfo;
    }
}
