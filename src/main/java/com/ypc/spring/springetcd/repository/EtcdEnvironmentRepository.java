package com.ypc.spring.springetcd.repository;

import com.ypc.spring.springetcd.config.EtcdConfigProperties;
import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.Client;
import io.etcd.jetcd.KeyValue;
import io.etcd.jetcd.kv.GetResponse;
import io.etcd.jetcd.options.GetOption;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.config.environment.Environment;
import org.springframework.cloud.config.environment.PropertySource;
import org.springframework.cloud.config.server.environment.EnvironmentRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Configuration
@Profile("etcd")
public class EtcdEnvironmentRepository implements EnvironmentRepository, Ordered {

    private final Client client;

    private final EtcdConfigProperties configProperties;

    public EtcdEnvironmentRepository(Client client, EtcdConfigProperties configProperties) {
        this.client = client;
        this.configProperties = configProperties;
    }

    @SneakyThrows
    @Override
    public Environment findOne(String application, String profile, String label) {
        String queryKey = "/" + application + "/" + profile;
        GetOption option = GetOption.newBuilder().isPrefix(true).build();
        GetResponse response = client.getKVClient().get(ByteSequence.from(queryKey, StandardCharsets.UTF_8), option).get();
        List<KeyValue> list = response.getKvs();
        // 配置文件
        Map<String, String> properties = new HashMap<>();
        for (KeyValue kv : response.getKvs()) {
            String key = kv.getKey().toString(StandardCharsets.UTF_8);
            String value = kv.getValue().toString(StandardCharsets.UTF_8);
            key = key.replace(queryKey, "").replace("/", "");
            log.info("key={} ,value={}", key, value);
            properties.put(key, value);
        }
        Environment environment = new Environment(application, profile);
        //
        String propertyName = application + "-" + profile + ".properties";
        environment.add(new PropertySource(propertyName, properties));
        propertyName = "application-" + profile + ".properties";
//        environment.add(new PropertySource(propertyName, properties));

//        environment.add(new PropertySource(application + ".properties", properties));
        return environment;
    }

    @Override
    public Environment findOne(String application, String profile, String label, boolean includeOrigin) {
        return EnvironmentRepository.super.findOne(application, profile, label, includeOrigin);
    }

    @Override
    public int getOrder() {
        return configProperties.getOrder();
    }
}
