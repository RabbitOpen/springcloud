package com.cloud.poc.server;

import com.cloud.poc.common.RoleService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
@RequestMapping("/role")
public class RoleServer implements RoleService {

    public static void main(String[] args) {
        SpringApplication.run(RoleServer.class);
    }

    @GetMapping("/getRole")
    @ResponseBody
    @Override
    public String getRole() {
        return "my-role";
    }
}
