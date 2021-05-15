package com.cloud.poc.gateway.lb;

import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class LoadBalanceRule implements IRule {

    @Autowired
    SpringClientFactory clientFactory;

    @Autowired
    CustomizedLoadBalance loadBalance;

    @Override
    public Server choose(Object key) {
        List<Server> allServers = clientFactory.getLoadBalancer(loadBalance.getServiceId()).getAllServers();
        return allServers.get(0);
    }

    @Override
    public void setLoadBalancer(ILoadBalancer lb) {

    }

    @Override
    public ILoadBalancer getLoadBalancer() {
        return null;
    }
}
