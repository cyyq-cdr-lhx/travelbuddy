package com.edu.hit.demo.model;

import java.util.List;

public class Route {
    private String start;
    private String destination;
    private String distance;
    private String duration;
    private List<List<Double>> path; // 存储路径点的经纬度列表

    // getters 和 setters
    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public List<List<Double>> getPath() {
        return path;
    }

    public void setPath(List<List<Double>> path) {
        this.path = path;
    }
}
