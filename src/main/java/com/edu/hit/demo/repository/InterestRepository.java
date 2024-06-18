package com.edu.hit.demo.repository;

import com.edu.hit.demo.model.Interests;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InterestRepository extends JpaRepository<Interests, String> {
    Interests findByEmail(String email);
}
