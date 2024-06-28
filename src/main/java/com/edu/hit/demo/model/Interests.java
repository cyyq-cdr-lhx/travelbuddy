package com.edu.hit.demo.model;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
public class Interests {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    private String email;

    private String startcity;
    private  String destination;
    //private  String season;???
    private Integer duration;//???
    private String startDay;
    private String endDay;

    public Integer getId(){return id;}
    public void setId(Integer id){ this.id = id;}

    public String getEmail(){return email;}
    public void setEmail(String email){this.email = email;}
    public String getStartcity() {  return startcity; }

    public void setStartcity(String startcity) {this.startcity = startcity;  }

    public String getDestination() { return destination;    }

    public void setDestination(String destination) {this.destination = destination;    }

    //public String getSeason() { return season;   }

    //public void setSeason(String season) { this.season = season;    }

    public Integer getDuration() { return duration;    }

    public void setDuration(Integer duration) {  this.duration = duration;    }

    public String getStartDay() { return startDay;    }

    public void setStartDay(String startDay) {  this.startDay = startDay;    }

    public String getEndDay() { return endDay;    }

    public void setEndDay(String endDay) {  this.endDay = endDay;    }


}