package com.cloud.poc.common;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("user-server")
public interface UserService {

    @GetMapping("/user/getUserName")
    String getUserName();

    @GetMapping("/user/getUser")
    User getUser();
}
