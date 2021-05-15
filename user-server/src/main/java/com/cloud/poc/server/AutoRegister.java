package com.cloud.poc.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.zookeeper.serviceregistry.ServiceInstanceRegistration;
import org.springframework.cloud.zookeeper.serviceregistry.ZookeeperRegistration;
import org.springframework.cloud.zookeeper.serviceregistry.ZookeeperServiceRegistry;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component
public class AutoRegister {

    @Autowired
    private ZookeeperServiceRegistry serviceRegistry;

//    @PostConstruct
    public void init() {
        ZookeeperRegistration registration = ServiceInstanceRegistration.builder()
                .defaultUriSpec()
                .address("localhost")
                .port(8082)
                .name("user-service")
                .build();
        this.serviceRegistry.register(registration);
    }

    @PreDestroy
    public void destroy() {
        this.serviceRegistry.close();
    }
}
