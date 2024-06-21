package com.edu.hit.demo.repository;

import com.edu.hit.demo.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByOrderByLikesDesc();
    List<Post> findByEmail(String email);




}

