package com.edu.hit.demo.repository;

import com.edu.hit.demo.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
public interface UserRepository extends JpaRepository<Users, Integer>{
    // 自定义查询方法
    Users findByUsername(String username);
    Users findByEmail(String email);
}
