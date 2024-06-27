package com.edu.hit.demo.repository;

import com.edu.hit.demo.model.Interests;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterestRepository extends JpaRepository<Interests, String> {
    List<Interests> findByEmail(String email);
    Interests findById(Integer id);
    void deleteById(Integer id);





}
