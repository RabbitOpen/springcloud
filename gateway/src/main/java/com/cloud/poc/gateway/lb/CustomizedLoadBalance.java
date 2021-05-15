package com.cloud.poc.gateway.lb;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.netflix.ribbon.RibbonLoadBalancerClient;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.context.annotation.Configuration;

/**
 * 自定义负载均衡算法(选第一个)
 */
@Configuration
public class CustomizedLoadBalance extends RibbonLoadBalancerClient  {

    ThreadLocal<String> context = new ThreadLocal<>();

    public CustomizedLoadBalance(SpringClientFactory clientFactory) {
        super(clientFactory);
    }

    @Override
    public ServiceInstance choose(String serviceId) {
        try {
            context.set(serviceId);
            return super.choose(serviceId);
        } finally {
            context.remove();
        }
    }

    public String getServiceId() {
        return context.get();
    }

}
