package com.edu.hit.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GeocodeResult {
    private Geocode[] geocodes;

    public Geocode[] getGeocodes() {
        return geocodes;
    }

    public void setGeocodes(Geocode[] geocodes) {
        this.geocodes = geocodes;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Geocode {
        private String location;

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }
    }
}
