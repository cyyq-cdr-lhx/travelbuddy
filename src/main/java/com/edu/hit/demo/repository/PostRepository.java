package com.edu.hit.demo.repository;

import com.edu.hit.demo.model.Image;
import com.edu.hit.demo.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findAllByOrderByLikesDesc();




}

