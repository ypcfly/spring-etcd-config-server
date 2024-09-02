package com.ypc.spring.springetcd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableConfigServer
@EnableConfigurationProperties
@SpringBootApplication
//@EnableWebSecurity
public class SpringEtcdApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringEtcdApplication.class, args);
    }

}
