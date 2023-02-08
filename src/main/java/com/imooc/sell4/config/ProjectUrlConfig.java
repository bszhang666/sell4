package com.imooc.sell4.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "projecturl")
public class ProjectUrlConfig {

    public String wecharMpAuthorize;

    public String wechatOpenAuthorize;

    public String sell;


}
