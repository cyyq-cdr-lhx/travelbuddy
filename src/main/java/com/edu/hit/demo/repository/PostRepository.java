package com.edu.hit.demo.repository;

import com.edu.hit.demo.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    //List<Post> findAllByOrderByLikesDesc();
    //List<Post> findByEmail(String email);
    @Query("SELECT p FROM Post p WHERE p.email = :email ORDER BY p.likes ASC")
    List<Post> findByEmail(@Param("email") String email);




}

