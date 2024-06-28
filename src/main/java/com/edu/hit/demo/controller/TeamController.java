package com.edu.hit.demo.controller;


import com.edu.hit.demo.model.Invitee;
import com.edu.hit.demo.model.Users;
import com.edu.hit.demo.service.InterestService;
import com.edu.hit.demo.service.InviteService;
import com.edu.hit.demo.service.MatchingService;
import com.edu.hit.demo.service.UserService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.ArrayList;
import java.util.List;

@SessionAttributes({"homeUser","matchedUsers","inviters","myInvitees"})
@Controller
public class TeamController {
    @Autowired
    private MatchingService matchingService;
    @Autowired
    private UserService userService;
    @Autowired
    private InviteService inviteService;

    @GetMapping("/autoTeam")
    public String autoTeam(@ModelAttribute("homeUser") Users hUser, Model model) {
        List<Users> matchedUsers = matchingService.findTeammates(hUser);
        //List<Users> allInviters  = userService.findInvitersByInvitee(hUser);
        List<Invitee> inviters = inviteService.findAllByInvitee(hUser);

        List<Invitee> myInvitees =inviteService.findAllByInviter(hUser);
        System.out.println(hUser.getEmail());

        model.addAttribute("matchedUsers", matchedUsers);
        model.addAttribute("inviters",inviters);
        model.addAttribute("myInvitees",myInvitees);
        //model.addAttribute("homeUser",hUser);
        return "autoTeam";
    }
    @GetMapping("backToAutoTeam")
    public String backToAutoTeam(@ModelAttribute("homeUser")Users hUser,
                                 Model model){
        List<Users> matchedUsers = matchingService.findTeammates(hUser);
        //List<Users> allInviters  = userService.findInvitersByInvitee(hUser);
        List<Invitee> inviters = inviteService.findAllByInvitee(hUser);

        List<Invitee> myInvitees =inviteService.findAllByInviter(hUser);
        System.out.println(hUser.getEmail());
        model.addAttribute("inviters",inviters);
        model.addAttribute("myInvitees",myInvitees);
        model.addAttribute("matchedUsers", matchedUsers);
        //model.addAttribute("homeUser",hUser);
        return "autoTeam";
    }

    @GetMapping("/searchUsers")
    public String searchUsers(@RequestParam("username") String username, Model model) {
        List<Users> searchedUsers = userService.findUsersByUsernameIgnoreCase(username);
        model.addAttribute("searchedUsers", searchedUsers);
        return "autoTeam";
    }

}