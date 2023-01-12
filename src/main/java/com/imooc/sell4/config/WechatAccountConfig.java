package com.imooc.sell4.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 用作和配置文件的配置
 */
@ConfigurationProperties(prefix = "wechat")
@Data
@Component
public class WechatAccountConfig {

    private String mpAppId;

    private String mpAppSecret;


}
