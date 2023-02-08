package com.imooc.sell4.handler;

import com.imooc.sell4.config.ProjectUrlConfig;
import com.imooc.sell4.exception.SellerAuthorizeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@Component
@ControllerAdvice//常用于exceptionHandler的处理加强
public class SellerExceptionHandler {

    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    //拦截登录异常
    //拦截到这种异常就进行处理和跳转
    @ExceptionHandler(value = SellerAuthorizeException.class)
    public ModelAndView handerSellerAuthorizeException()
    {
        return new ModelAndView("redirect:".concat(projectUrlConfig.getWechatOpenAuthorize()).
                concat("/sell/wechat/qrAuthorize").
                concat("?returnUrl=").
                concat(projectUrlConfig.getSell()).
                concat("/sell/seller/login"));
    }

}
