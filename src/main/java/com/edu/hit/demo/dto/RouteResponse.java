package com.edu.hit.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RouteResponse {
    private PathResponse route;

    @JsonProperty("route")
    public PathResponse getRoute() {
        return route;
    }

    @JsonProperty("route")
    public void setRoute(PathResponse route) {
        this.route = route;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PathResponse {
        private Path[] paths;

        @JsonProperty("paths")
        public Path[] getPaths() {
            return paths;
        }

        @JsonProperty("paths")
        public void setPaths(Path[] paths) {
            this.paths = paths;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Path {
        private String distance;
        private String duration;
        private Step[] steps;

        @JsonProperty("distance")
        public String getDistance() {
            return distance;
        }

        @JsonProperty("distance")
        public void setDistance(String distance) {
            this.distance = distance;
        }

        @JsonProperty("duration")
        public String getDuration() {
            return duration;
        }

        @JsonProperty("duration")
        public void setDuration(String duration) {
            this.duration = duration;
        }

        @JsonProperty("steps")
        public Step[] getSteps() {
            return steps;
        }

        @JsonProperty("steps")
        public void setSteps(Step[] steps) {
            this.steps = steps;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Step {
        private String polyline;

        @JsonProperty("polyline")
        public String getPolyline() {
            return polyline;
        }

        @JsonProperty("polyline")
        public void setPolyline(String polyline) {
            this.polyline = polyline;
        }
    }
}