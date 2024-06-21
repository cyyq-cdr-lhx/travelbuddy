package com.edu.hit.demo.controller;

import com.edu.hit.demo.model.Route;
import com.edu.hit.demo.service.RouteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/route")
public class RouteController {

    private static final Logger logger = LoggerFactory.getLogger(RouteController.class);
    private final RouteService routeService;

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @GetMapping("/routePlan")
    public String showRoutePlanPage(Model model) {
        return "routePlan";
    }

    @PostMapping("/routePlan")
    public String planRoute(@RequestParam("start") String start,
                            @RequestParam("destination") String destination, Model model) {
        logger.info("Received start: " + start);
        logger.info("Received destination: " + destination);

        // 调用服务获取路线信息
        Route route = routeService.planRoute(start, destination);

        // 将route对象添加到模型
        model.addAttribute("route", route);
        model.addAttribute("start", start);
        model.addAttribute("destination", destination);
        return "routeDisplay";
    }
}
