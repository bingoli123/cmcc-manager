package com.cmcc.config.dubbo;

import com.alibaba.dubbo.config.RegistryConfig;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.List;

/**
 *
 */
@Configuration
@ConfigurationProperties(prefix="zookeeper")
@ConditionalOnExpression("'${zookeeper.address}'.length() > 0")
@Data
@Slf4j
public class DubboRegistryAutoConfig {

    private final static String REGISTRY_ID = new String("demoRegistry");
    private List<String> address;
    private Integer timeout;
    private String protocol;
    private String namespace;
    private String file;

    @Bean("demoRegistry")
    @Primary
    @ConditionalOnClass({RegistryConfig.class})
    public RegistryConfig registryConfig(){
        RegistryConfig registry = new RegistryConfig();
        log.info(" >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>(@ConditionalOnBean(RegistryConfig.class)) Start configuration Dubbo RegistryConfig.");
        String regAddress = changeAddress(address);
        log.info("Change Zookeeper Address: " + regAddress);
        registry.setAddress(regAddress);
        registry.setId(REGISTRY_ID);
        registry.setTimeout(timeout);
        registry.setProtocol(protocol);
        if (file !=null && !"".equals( file )) registry.setFile(file);
        log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> registry_id : " + registry.getId());
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


    /*@Bean(initMethod = "init")
    @ConditionalOnClass({ZookeeperRegistryCenter.class})
    public ZookeeperRegistryCenter regCenter() {
        log.info("创建和elastic job相关的zookeeper配置");
        String regAddress = changeAddress(address);
        return new ZookeeperRegistryCenter(new ZookeeperConfiguration(regAddress, namespace));
    }*/
}