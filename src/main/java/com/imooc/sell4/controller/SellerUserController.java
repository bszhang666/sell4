package com.imooc.sell4.controller;

import com.imooc.sell4.config.ProjectUrlConfig;
import com.imooc.sell4.constant.RedisConstant;
import com.imooc.sell4.dataobject.SellerInfo;
import com.imooc.sell4.service.SellerService;
import com.imooc.sell4.utils.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping("/seller")
public class SellerUserController {

    @Autowired
    private SellerService sellerService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    @GetMapping("/login")
    public ModelAndView login(@RequestParam("openid")String openid,
                              HttpServletResponse response,
                              Map<String,Object>map)
    {
        //1.数据库查询openid
        SellerInfo sellerInfo=sellerService.findSellerInfoByOpenid(openid);
        if(sellerInfo==null)
        {
            map.put("msg","登录失败");
            map.put("url","/sell/seller/order/list");
            return new ModelAndView("common/error",map);
        }

        //2.设置token至redis
        String token= UUID.randomUUID().toString();
        Integer expire= RedisConstant.EXPIRE;
        stringRedisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_EXPIRE, token), openid, expire, TimeUnit.SECONDS);

        //3.设置token到cookie
        CookieUtil.set(response,"token",token,expire);

        return new ModelAndView("redirect:"+ projectUrlConfig.getSell()+"/sell/seller/order/list");
    }

    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request,HttpServletResponse response,Map<String,Object>map)
    {
        //1.cookie中查询
        Cookie cookie=CookieUtil.get(request,"token");
        if(cookie!=null)
        {
            //2.reids清楚
            stringRedisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_EXPIRE,cookie.getValue()));
            //3 cookie清楚
            CookieUtil.set(response,"token",null,0);//设置为0即为清除

        }
        map.put("msg","登出成功");
        map.put("url","/sell/seller/order/list");
        return new ModelAndView("common/success",map);


    }
}
