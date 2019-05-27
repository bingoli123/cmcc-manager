package com.cmcc.config.dubbo;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Component;

@Component
@AutoConfigureAfter(DubboRegistryAutoConfig.class)
@ImportResource("classpath:/spring/framework-dubbo-*.xml")
public class EnableDubboXMLConfig {
}
