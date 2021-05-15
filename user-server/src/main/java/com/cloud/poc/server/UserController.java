package com.cloud.poc.server;

import com.cloud.poc.common.User;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("/getUserName")
    @ResponseBody
    public String getUserName() {
        return "zhangsan-8082";
    }

    @RequestMapping("/getUser")
    @ResponseBody
    public User getUser(HttpServletRequest request) throws IOException {
        logger.info("request: {}", headerString(request));
        User user = new User();
        user.setUsername("zhangsan-8082");
        return user;
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
