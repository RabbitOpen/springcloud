package com.cloud.poc.client.controller;

import com.cloud.poc.client.UserServiceImpl;
import com.cloud.poc.common.User;
import com.cloud.poc.common.UserService;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private UserService userService;

    @PostMapping("/getUser")
    @ResponseBody
    public User getUser(HttpServletRequest request) throws IOException {
        logger.info("request: {}", headerString(request));
        return userService.getUser();
    }

    private String headerString(HttpServletRequest request) throws IOException {
        Map<String, Object> map = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            map.put(key, request.getHeader(key));
        }
        return new ObjectMapper().writeValueAsString(map);
    }
}
