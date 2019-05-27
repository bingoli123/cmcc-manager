package com.cmcc.config.dubbo;

import com.alibaba.dubbo.config.RegistryConfig;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.List;

//import com.haier.HSI.currency.dubbo.common.CommonUtil;

@Configuration
@ConfigurationProperties(prefix="zookeeper")
@ConditionalOnClass({RegistryConfig.class})
@Data
@Slf4j
public class DubboConfig {
    private List<String> address;
    private Integer timeout;
    private String protocol;


    @Bean
    @Primary
    public RegistryConfig registryConfig(){
        RegistryConfig registry = new RegistryConfig();
        log.info(" >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>(@ConditionalOnMissingBean(RegistryConfig.class)) Start configuration Dubbo RegistryConfig.");
        String regAddress = changeAddress(address);
        log.info("Change Zookeeper Address: " + regAddress);
        registry.setAddress(regAddress);
        registry.setTimeout(timeout != null ? timeout : 10000);
        registry.setProtocol("zookeeper");
        return registry;
    }

    private String changeAddress(List<String> addressList){
        String regAddress = "";
        if (addressList == null || addressList.size() < 1){
            log.info("You have not configured the zookeeper information to enable the default configuration.");
            regAddress = "127.0.0.1:2181";
        }else {
            for (String oneAddress : addressList) {
                regAddress = regAddress + "," + oneAddress;
            }
            regAddress = regAddress.substring(1);
        }
        return regAddress;
    }
}
