package com.edu.hit.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
@Entity
public class Comment {
/*保存评论id 内容 用户邮箱
* 多对一映射图片
*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    private String email;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post2 post;

    public Comment() {}

    public Comment(String text) {
        this.text = text;
    }

    // Getters and Setters

    public void setId(Long id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setEmail(String email){this.email = email;}

    public void setPost(Post2 post) {
        this.post = post;
    }
}

