package com.edu.hit.demo.model;


import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;


import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Post {

    /*
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String username;
    private String caption;


    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL, orphanRemoval = true) // 一对多关联，Image 类中需要有一个 Post 类型的字段
    private List<Image> imageData = new ArrayList<>();


    private int likes = 0;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

     */
    /*保存上传图片的用户邮箱、用户名
     *图片标题、点赞数、一对多映射评论
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String username;
    private String caption;

    private int likes = 0;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();



    @ElementCollection
    private List<String> likedBy = new ArrayList<>();

    // Getters and Setters
    public List<String> getLikedBy() {
        return likedBy;
    }

    public void setLikedBy(List<String> likedBy) {
        this.likedBy = likedBy;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }


    public void setLikes(int likes) {
        this.likes = likes;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }



}