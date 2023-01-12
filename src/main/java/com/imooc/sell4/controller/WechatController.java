package com.imooc.sell4.controller;

import com.imooc.sell4.config.WechatMpConfig;
import com.imooc.sell4.enums.ResultEnum;
import com.imooc.sell4.exception.SellException;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URLEncoder;

@Slf4j
@Controller
@RequestMapping("/wechat")
public class WechatController {

    @Autowired
    private WechatMpConfig wechatMpConfig;

    @Autowired
    private WxMpService wxMpService;

    @GetMapping("/authorize")
    public String authorize(@RequestParam("returnUrl")String returnUrl)
    {
        //1.配置
        //配置已经使用config中完成
        //2.调用方法
        String url="http://zbs999.natapp1.cc/sell/wechat/userInfo";
        String redirectUrl = wxMpService.oauth2buildAuthorizationUrl(url, WxConsts.OAUTH2_SCOPE_USER_INFO, URLEncoder.encode(returnUrl));//第一个参数是redirectUrl

        //log.info("获取code,result={}",redirectUrl);
        return "redirect:"+redirectUrl;
    }


    @GetMapping("/userInfo")
    public String userInfo(@RequestParam("code")String code,
                           @RequestParam("state")String returnUrl)
    {
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken=new WxMpOAuth2AccessToken();
        try{
            wxMpOAuth2AccessToken=wxMpService.oauth2getAccessToken(code);
        }catch (WxErrorException e) {
            log.error("微信网页授权,{}",e);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),e.getError().getErrorMsg());
        }
        String openId=wxMpOAuth2AccessToken.getOpenId();
        return "redirect:"+returnUrl + "?openid="+openId;

    }
}
