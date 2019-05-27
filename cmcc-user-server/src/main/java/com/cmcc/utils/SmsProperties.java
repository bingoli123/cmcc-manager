package com.cmcc.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p>Create Time: 2019年05月09日 10:00          </p>
 * <p>C@author lidongbin               </p>
 **/
@ConfigurationProperties(prefix="sms")
@Data
@Component
public class SmsProperties {

    private String ecName;
    private String apId;
    private String sign;
    private String secretKey;
    private String normalUrl;
    private String templateUrl;
}
