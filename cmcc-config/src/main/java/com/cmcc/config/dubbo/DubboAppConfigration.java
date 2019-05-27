package com.cmcc.config.dubbo;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ConsumerConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.cmcc.config.command.DubboServiceLatchCommandLineRunner;
import com.cmcc.config.properties.BaseProperties;
import com.cmcc.config.properties.DubboCoreProperties;
import com.cmcc.config.util.StringHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;


/**
 * conf
 *
 * @author YL-S
 * @Create 2018/11/15
 */
@Slf4j
@Order
@Configuration
@EnableConfigurationProperties({DubboCoreProperties.class, BaseProperties.class})
public class DubboAppConfigration {
//    private static final Logger log = LoggerFactory.getLogger(DubboAppConfiguration.class);
    @Autowired
    private BaseProperties baseProperties;
    @Autowired
    private DubboCoreProperties dubboCoreProperties;

    public DubboAppConfigration() {
    }

    @Bean(
            name = {"com.alibaba.dubbo.config.ApplicationConfig"}
    )
    public ApplicationConfig applicationConfig() {
        ApplicationConfig config = new ApplicationConfig();
        if (StringHelper.isNullOrEmpty(this.baseProperties.getApp())) {
            throw new IllegalStateException("AppName can't be empty, please make sure that 'interconn.project' and 'interconn.app' have been set");
        } else {
            config.setName(this.baseProperties.getApp());
            config.setLogger("slf4j");
            return config;
        }
    }

    @Bean(
            name = {"com.alibaba.dubbo.config.RegistryConfig"}
    )
    @ConditionalOnProperty(
            prefix = "dubbo",
            name = {"registry"},
            havingValue = "",
            matchIfMissing = false
    )
    public RegistryConfig registryConfig() {
        RegistryConfig config = new RegistryConfig();
        config.setAddress(this.dubboCoreProperties.getRegistry());
        config.setProtocol("zookeeper");
        config.setTimeout(this.dubboCoreProperties.getTimeout());
        config.setVersion(this.dubboCoreProperties.getVersion());
        return config;
    }

    @Bean(
            name = {"com.alibaba.dubbo.config.ProtocolConfig"}
    )
    @ConditionalOnProperty(
            prefix = "dubbo",
            name = {"mode"},
            havingValue = "provider",
            matchIfMissing = true
    )
    public ProtocolConfig protocolConfig() {
        ProtocolConfig config = new ProtocolConfig();
        config.setName("dubbo");
        if (this.dubboCoreProperties.getHost() != null) {
            config.setHost(this.dubboCoreProperties.getHost());
        }

        if (this.dubboCoreProperties.getSerialization() != null) {
            config.setSerialization(this.dubboCoreProperties.getSerialization());
        }

        config.setPort(this.dubboCoreProperties.getPort());
        config.setThreads(this.dubboCoreProperties.getThreads());
        config.setHeartbeat(this.dubboCoreProperties.getHeartBeats());
        return config;
    }

    @Bean
    public ConsumerConfig consumerConfig() {
        ConsumerConfig config = new ConsumerConfig();
        config.setTimeout(Integer.valueOf(10000));
        return config;
    }

    @Bean
    @ConditionalOnProperty(
            prefix = "dubbo",
            name = {"mode"},
            havingValue = "provider",
            matchIfMissing = true
    )
    public DubboServiceLatchCommandLineRunner configureDubboServiceLatchCommandLineRunner() {
        log.info("Auto start dubbo configuration");
        log.info("project    --> {}", this.baseProperties.getProject());
        log.info("appName    --> {}", this.baseProperties.getApp());
        log.info("port       --> {}", this.dubboCoreProperties.getPort());
        log.info("threads    --> {}", this.dubboCoreProperties.getThreads());
        log.info("timeout    --> {}", this.dubboCoreProperties.getTimeout());
        log.info("heartBeats --> {}", this.dubboCoreProperties.getHeartBeats());
        log.info("host       --> {}", this.dubboCoreProperties.getHost());
        log.info("serialization --> {}", this.dubboCoreProperties.getSerialization());
        return new DubboServiceLatchCommandLineRunner();
    }

//    @Bean
//    public DubboServiceListenerBean dubboServiceListenerBean() {
//        return new DubboServiceListenerBean(this.baseProperties.getApp());
//    }
//
//    @Bean
//    @ConditionalOnMissingBean
//    public DubboCollector DubboCollector(MonitorConfig monitorConfig) {
//        return DubboCollector.create(monitorConfig);
//    }
//
//    @Bean
//    @ConditionalOnMissingBean
//    public JvmCollector jvmCollector(MonitorConfig monitorConfig) {
//        return JvmCollector.create(monitorConfig);
//    }



}
