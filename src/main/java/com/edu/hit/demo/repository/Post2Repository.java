package com.edu.hit.demo.repository;


import com.edu.hit.demo.model.Comment;
import com.edu.hit.demo.model.Post2;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface Post2Repository extends JpaRepository<Post2, Long> {
    List<Post2> findAllByOrderByLikesDesc();
    List<Post2> findByEmail(String email);




}

