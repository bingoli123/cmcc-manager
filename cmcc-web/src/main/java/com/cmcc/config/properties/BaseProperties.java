package com.cmcc.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * BaseProperties
 *
 * @author YL-S
 * @Create 2018/11/15
 */
@ConfigurationProperties(
        prefix = "interconn"
)
public class BaseProperties {
    String project;
    String app;

    public BaseProperties() {
    }

    public String getProject() {
        return this.project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getApp() {
        return this.app;
    }

    public void setApp(String app) {
        this.app = app;
    }
}
