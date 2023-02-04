package com.imooc.sell4.service;

import com.imooc.sell4.dataobject.SellerInfo;

public interface SellerService {
    SellerInfo findSellerInfoByOpenid(String openid);
}
