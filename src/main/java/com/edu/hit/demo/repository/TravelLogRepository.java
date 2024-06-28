package com.edu.hit.demo.repository;

import com.edu.hit.demo.model.TravelLog;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TravelLogRepository extends JpaRepository<TravelLog, Long> {
    List<TravelLog> findByUserId(Integer userId);
}