package com.edu.hit.demo.repository;

import com.edu.hit.demo.model.Comment;
//import com.edu.hit.demo.model.Post;
import com.edu.hit.demo.model.Post2;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CommentRepository extends JpaRepository<Comment, Long> {
    //List<Comment> findByPost(Post post);
    List<Comment> findByPost(Post2 post);
    List<Comment> findByEmail(String email);

}
