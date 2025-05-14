package ru.master.service.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "application.yookassa")
public class YooKassaConfig {

    private String shopId;
    private String secretKey;
    private String returnUrl;
    private boolean testMode;
}
