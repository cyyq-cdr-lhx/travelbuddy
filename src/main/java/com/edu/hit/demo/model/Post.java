//package com.edu.hit.demo.model;
//
//
//import jakarta.persistence.*;
//
//import lombok.Getter;
//import lombok.Setter;
//import org.hibernate.annotations.Type;
//
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Getter
//@Setter
//@Entity
//public class Post {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String email;
//
//    private String username;
//    private String caption;
//
//
//    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL, orphanRemoval = true) // 一对多关联，Image 类中需要有一个 Post 类型的字段
//    private List<Image> imageData = new ArrayList<>();
//
//
//    private int likes = 0;
//
//    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Comment> comments = new ArrayList<>();
//
//// Getters and Setters
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public String getCaption() {
//        return caption;
//    }
//
//    public void setCaption(String caption) {
//        this.caption = caption;
//    }
//
//    public List<Image> getImageData() {
//        return imageData;
//    }
//
//    public void setImageData(List<Image> imageData) {
//        this.imageData = imageData;
//    }
//
//
//    public int getLikes() {
//        return likes;
//    }
//
//    public void setLikes(int likes) {
//        this.likes = likes;
//    }
//
//    public List<Comment> getComments() {
//        return comments;
//    }
//
//    public void setComments(List<Comment> comments) {
//        this.comments = comments;
//    }
//
//}