package com.edu.hit.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class TravelLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createdDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    public TravelLog() {}

    public TravelLog(String title, String content, LocalDateTime createdDate, Users user) {
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.user = user;
    }

    // Getters and Setters are generated by Lombok
}