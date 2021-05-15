package com.cloud.poc.client;

import com.cloud.poc.common.RoleService;
import com.cloud.poc.common.UserService;
import com.netflix.loadbalancer.IRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Configuration
@Component
public class UserServiceImpl implements InitializingBean {

//    @Bean
//    @LoadBalanced
//    public RestTemplate restTemplate() {
//        return new RestTemplate();
//    }

    /**
     * 自定义负载均衡算法
     * @return
     */
    @Bean
    @Scope("prototype")
    public IRule myRule() {
        // 默认算法
//        return new RoundRobinRule();
        return new CustomizedLoadBalanceRule();
    }


    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

//    // ribbon
//    @Autowired
//    RestTemplate restTemplate;
//
//    @Autowired
//    public LoadBalancerClient loadBalancerClient;


    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("feign getUser: {}", userService.getUser().getUsername());
        /*for (int i = 0; i < 5; i++) {
            ServiceInstance balance = loadBalancerClient.choose("user-server");
            String context = String.format("http://%s:%d", balance.getHost(), balance.getPort());
            logger.info("feign getUser: {}", userService.getUser().getUsername());
            logger.info("feign getUserName: {}", userService.getUserName());
            logger.info("feign getRole: {}", roleService.getRole());
            logger.info("ribbon getUser: {}", restTemplate.getForObject(context + "/getUser", User.class).getUsername());
            logger.info("ribbon getUserName: {}",  restTemplate.getForObject(context + "/getUserName", String.class));
        }*/

    }

}
