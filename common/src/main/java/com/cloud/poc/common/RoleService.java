package com.cloud.poc.common;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("role-server")
public interface RoleService {

    @GetMapping("/role/getRole")
    String getRole();
}
