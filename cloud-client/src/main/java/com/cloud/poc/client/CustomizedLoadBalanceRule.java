package com.cloud.poc.client;

import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.Server;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 自定义算法，永远选第一个节点
 */
public class CustomizedLoadBalanceRule implements IRule {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private ILoadBalancer iLoadBalancer;
    private Map<String, ILoadBalancer> balancerMap = new ConcurrentHashMap<>();

    public CustomizedLoadBalanceRule() {
    }

    public CustomizedLoadBalanceRule(ILoadBalancer loadBalancer) {
        setLoadBalancer(loadBalancer);
    }

    @Override
    public Server choose(Object o) {
//        logger.info("choose: {}", o);
        List<Server> servers = iLoadBalancer.getAllServers();
        return CollectionUtils.isEmpty(servers) ? null : servers.get(0);
    }

    @Override
    public void setLoadBalancer(ILoadBalancer iLoadBalancer) {
        BaseLoadBalancer loadBalancer = (BaseLoadBalancer) iLoadBalancer;
        this.balancerMap.put(loadBalancer.getName(), loadBalancer);
        this.iLoadBalancer = iLoadBalancer;
    }

    @Override
    public ILoadBalancer getLoadBalancer() {
        return null;
    }

}
