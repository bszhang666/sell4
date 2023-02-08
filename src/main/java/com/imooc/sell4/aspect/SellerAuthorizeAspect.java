package com.imooc.sell4.aspect;

import com.imooc.sell4.constant.RedisConstant;
import com.imooc.sell4.exception.SellerAuthorizeException;
import com.imooc.sell4.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@Slf4j
public class SellerAuthorizeAspect {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @Pointcut("execution(public * com.imooc.sell4.controller.Seller*.*(..) )"+
    "&& !execution(public * com.imooc.sell4.controller.SellerUserController.*(..))")
    public void vertify()
    {


    }

    @Before("vertify()")
    public void doVertify()
    {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest httpRequest=requestAttributes.getRequest();

        Cookie cookie= CookieUtil.get(httpRequest,"token");
        if(cookie==null)
        {
            log.warn("登录校验cookir中查不到token");
            throw new SellerAuthorizeException();
        }
        String tokenValue = stringRedisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_EXPIRE, cookie.getValue()));
        if(StringUtils.isEmpty(tokenValue))
        {
            log.warn("登录校验redis中查不到token");
            throw new SellerAuthorizeException();
        }

    }
}
