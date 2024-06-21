package com.edu.hit.demo.service;

import com.edu.hit.demo.dto.GeocodeResult;
import com.edu.hit.demo.dto.RouteResponse;
import com.edu.hit.demo.dto.RouteResponse.Path;
import com.edu.hit.demo.dto.RouteResponse.Step;
import com.edu.hit.demo.model.Route;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class RouteService {

    private final RestTemplate restTemplate;
    private final String apiKey = "4880eef3e6acfd8daac229f8611d407d";

    public RouteService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Route planRoute(String startName, String destinationName) {
        String startCoordinates = getLocationCoordinates(startName);
        String destinationCoordinates = getLocationCoordinates(destinationName);
        return getRouteDetails(startCoordinates, destinationCoordinates, startName, destinationName);
    }

    private String getLocationCoordinates(String address) {
        String url = String.format("https://restapi.amap.com/v3/geocode/geo?address=%s&key=%s", address, apiKey);
        GeocodeResult result = restTemplate.getForObject(url, GeocodeResult.class);
        if (result != null && result.getGeocodes().length > 0) {
            return result.getGeocodes()[0].getLocation();
        }
        return null;
    }

    private Route getRouteDetails(String start, String destination, String startName, String destinationName) {
        String url = String.format("https://restapi.amap.com/v3/direction/driving?origin=%s&destination=%s&key=%s",
                start, destination, apiKey);
        RouteResponse response = restTemplate.getForObject(url, RouteResponse.class);

        Route route = new Route();
        route.setStart(startName);
        route.setDestination(destinationName);
        if (response != null && response.getRoute() != null && response.getRoute().getPaths().length > 0) {
            Path path = response.getRoute().getPaths()[0];
            int distance_m = Integer.parseInt(path.getDistance());
            int distance_km = distance_m / 1000;
            route.setDistance(String.valueOf(distance_km));
            // 将 duration 从秒转换为分钟
            int durationInSeconds = Integer.parseInt(path.getDuration());
            int durationInMinutes = durationInSeconds / 60;
            route.setDuration(String.valueOf(durationInMinutes));

            // 解析路径点
            List<List<Double>> pathPoints = new ArrayList<>();
            for (Step step : path.getSteps()) {
                String[] coords = step.getPolyline().split(";");
                for (String coord : coords) {
                    String[] lngLat = coord.split(",");
                    List<Double> point = new ArrayList<>();
                    point.add(Double.valueOf(lngLat[0]));
                    point.add(Double.valueOf(lngLat[1]));
                    pathPoints.add(point);
                }
            }
            route.setPath(pathPoints);
        }
        return route;
    }
}
