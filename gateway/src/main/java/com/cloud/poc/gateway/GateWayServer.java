package com.cloud.poc.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class GateWayServer {

    public static void main(String[] args) {
        SpringApplication.run(GateWayServer.class);
    }

}
