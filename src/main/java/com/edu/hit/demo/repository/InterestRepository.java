package com.edu.hit.demo.repository;

import com.edu.hit.demo.model.Interests;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InterestRepository extends JpaRepository<Interests, String> {
    List<Interests> findByEmail(String email);
    Interests findById(Integer id);
    void deleteById(Integer id);





}