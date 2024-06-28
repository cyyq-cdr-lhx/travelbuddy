package com.edu.hit.demo.model;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

@Getter
@Setter
@Entity
public class RouteReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer routeId;  // 假设每条评论都关联到一条特定的路线
    private Integer rating;   // 评分
    private String comment;   // 用户的评论文本

    // 无需再定义 getter 和 setter，因为已经由 Lombok 自动生成
}