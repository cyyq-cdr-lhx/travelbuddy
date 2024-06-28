package com.edu.hit.demo.repository;

import com.edu.hit.demo.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<Users, Integer>{
    // 自定义查询方法
    Users findByUsername(String username);
    Users findByEmail(String email);
    @Query("SELECT u FROM Users u JOIN u.invitees i WHERE i.invitee = :invitee")
    List<Users> findUsersByInvitee(@Param("invitee") Users invitee);

    List<Users> findByUsernameIgnoreCase(String username);


}
