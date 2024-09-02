package com.ypc.spring.springetcd.config;


import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.Client;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.StandardCharsets;
import java.util.List;


@Configuration
@ConditionalOnBean({EtcdConfigProperties.class})
public class EtcdClientConfig {


    @Bean
    public Client createClient(EtcdConfigProperties etcdConfigProperties) {
        List<String> endpoints = etcdConfigProperties.getEndpoints();

        return Client.builder().endpoints(endpoints.toArray(new String[endpoints.size()]))
//                .namespace(ByteSequence.from(etcdConfigProperties.getNameSpace(), StandardCharsets.UTF_8))
                .user(ByteSequence.from(etcdConfigProperties.getUserName(),StandardCharsets.UTF_8))
                .password(ByteSequence.from(etcdConfigProperties.getPassword(), StandardCharsets.UTF_8)).build();
    }
}
