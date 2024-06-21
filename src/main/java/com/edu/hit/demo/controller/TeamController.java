package com.edu.hit.demo.controller;


import com.edu.hit.demo.model.Users;
import com.edu.hit.demo.service.MatchingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;

@SessionAttributes("homeUser")
@Controller
public class TeamController {
    @Autowired
    private MatchingService matchingService;
    @GetMapping("/autoTeam")
    public String autoTeam(@ModelAttribute("homeUser") Users hUser, Model model) {
        List<Users> matchedUsers = matchingService.findTeammates(hUser);
        model.addAttribute("matchedUsers", matchedUsers);
        return "autoTeam";
    }

}
