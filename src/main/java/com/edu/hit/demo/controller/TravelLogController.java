package com.edu.hit.demo.controller;

import com.edu.hit.demo.model.TravelLog;
import com.edu.hit.demo.model.Users;
import com.edu.hit.demo.repository.TravelLogRepository;
import com.edu.hit.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/logs")
@SessionAttributes("homeUser")
public class TravelLogController {

    @Autowired
    private TravelLogRepository travelLogRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/new")
    public String showLogWritePage(@ModelAttribute("homeUser") Users user, Model model) {
        model.addAttribute("log", new TravelLog());
        model.addAttribute("user", user);
        return "logWrite";
    }

    @PostMapping("/new")
    public String createLog(@RequestParam("title") String title,
                            @RequestParam("content") String content,
                            @ModelAttribute("homeUser") Users user, Model model) {
        if (user == null) {
            return "redirect:/";
        }
        TravelLog log = new TravelLog(title, content, LocalDateTime.now(), user);
        travelLogRepository.save(log);
        return "myLog";
    }

    @GetMapping("/myLogs")
    public String showMyLogs(@ModelAttribute("homeUser") Users user, Model model) {
        List<TravelLog> logs = travelLogRepository.findByUserId(user.getId());
        model.addAttribute("logs", logs);
        return "myLog";
    }
}