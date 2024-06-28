package com.edu.hit.demo.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@Entity
public class Likes {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //用户邮箱
    private String email;
    //post的id
    private Long postId;

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }


}
