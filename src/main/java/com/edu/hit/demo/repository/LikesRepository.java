package com.edu.hit.demo.repository;

import com.edu.hit.demo.model.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    Likes findByPostIdAndEmail(Long postid,String email);

    @Query("SELECT COUNT(l) FROM Likes l WHERE l.postId = :postId")
    Integer countByPostId(@Param("postId") Long postId);
}