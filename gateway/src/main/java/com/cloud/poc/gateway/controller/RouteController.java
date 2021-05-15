package com.cloud.poc.gateway.controller;

import com.cloud.poc.gateway.route.RouteManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequestMapping("/gateway")
public class RouteController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    RouteLocator definitionLocator;

    @Autowired
    RouteManager routeManager;

    @GetMapping("/getDefinition")
    @ResponseBody
    public List<Route> getDefinition() {
        try {
            Flux<Route> flux = definitionLocator.getRoutes();
            flux.subscribe();
            List<Route> definitions = flux.collectList().block();
            for (Route d : definitions) {
                logger.info("id: {}, uri: {}", d.getId(), d.getUri());
            }
            return definitions;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    @RequestMapping("/addUserRoute")
    @ResponseBody
    public String addUserRoute() {
        routeManager.addUserRoute();
        return "add success";
    }

    @RequestMapping("/deleteUserRoute")
    @ResponseBody
    public String deleteUserRoute() {
        routeManager.deleteUserRoute();
        return "delete success";
    }

    @RequestMapping("/getRoutes")
    @ResponseBody
    public List<RouteDefinition> getRoutes() {
        return routeManager.getRouteDefinitions();
    }
}
