package com.edu.hit.demo.repository;
import com.edu.hit.demo.model.Image;

import com.edu.hit.demo.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long>{
    List<Image> findByPost(Post post);

}
