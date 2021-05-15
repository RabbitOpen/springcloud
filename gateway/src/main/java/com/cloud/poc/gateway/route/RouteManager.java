package com.cloud.poc.gateway.route;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@org.springframework.context.annotation.Configuration
@Component
public class RouteManager implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher publisher;

    private Logger logger = LoggerFactory.getLogger(RouteManager.class);

    @Autowired
    RouteDefinitionWriter routeDefinitionWriter;

    @Autowired
    RouteDefinitionLocator routeDefinitionLocator;

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        logger.info("build routeLocator.............");
        return builder.routes()
//                .route("rule-user", p -> p.path("/user/**").uri("http://localhost:8082"))
                .route("rule-role", p -> p.path("/role/**").uri("lb://role-server"))
                .route("rule-gateway", p -> p.path("/gateway/**").uri("lb://gateway"))
                .build();
    }

    /**
     * 动态添加路由
     */
    public void addUserRoute() {
        try {
            logger.info("添加用户路由");
            RouteDefinition definition = new RouteDefinition();
            definition.setId("rule-user");
            PredicateDefinition predicate = new PredicateDefinition();
            predicate.setName("Path");
            predicate.addArg("pattern", "/user/**");
            definition.setPredicates(Arrays.asList(predicate));
            definition.setUri(new URI("http://localhost:8082"));
            routeDefinitionWriter.save(Mono.just(definition)).subscribe();
            this.publisher.publishEvent(new RefreshRoutesEvent(this));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 删除用户路由
     */
    public void deleteUserRoute() {
        try {
            logger.info("删除用户路由");
            routeDefinitionWriter.delete(Mono.just("rule-user"))
                    .onErrorResume(e -> {
                        logger.error(e.getMessage());
                        return Mono.empty();
                    }).subscribe();
            this.publisher.publishEvent(new RefreshRoutesEvent(this));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 查询动态添加的路由定义
     * @return
     */
    public List<RouteDefinition> getRouteDefinitions() {
        try {
            logger.info("查询路由");
            List<RouteDefinition> data = new ArrayList<>();
            routeDefinitionLocator.getRouteDefinitions().collectList().subscribe(routeDefinitions -> {
                data.addAll(routeDefinitions);
            });
            return data;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }
}
