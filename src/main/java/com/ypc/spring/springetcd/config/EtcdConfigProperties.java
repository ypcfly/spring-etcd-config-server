package com.ypc.spring.springetcd.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.config.server.support.EnvironmentRepositoryProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import java.util.List;


@Data
@Configuration
@ConfigurationProperties(prefix = "spring.cloud.config.server.etcd")
public class EtcdConfigProperties implements EnvironmentRepositoryProperties {

    private int order = Ordered.LOWEST_PRECEDENCE;

    private List<String> endpoints;

    private boolean enable;

    private String nameSpace;

    private String userName;

    private String password;

    @Override
    public void setOrder(int order) {
        this.order = order;
    }
}
