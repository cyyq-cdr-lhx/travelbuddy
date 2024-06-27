package com.edu.hit.demo.service;

import com.edu.hit.demo.model.TravelLog;
import com.edu.hit.demo.model.Users;
import com.edu.hit.demo.repository.TravelLogRepository;
import com.edu.hit.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TravelLogService {

    @Autowired
    private TravelLogRepository travelLogRepository;

    @Autowired
    private UserRepository userRepository;

    public TravelLog saveLog(String title, String content, Users user) {
        TravelLog log = new TravelLog(title, content, LocalDateTime.now(), user);
        return travelLogRepository.save(log);
    }

    public List<TravelLog> getLogsByUser(Integer userId) {
        return travelLogRepository.findByUserId(userId);
    }
}
