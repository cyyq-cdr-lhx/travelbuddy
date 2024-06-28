package com.edu.hit.demo.controller;

import com.edu.hit.demo.model.Invitee;
import com.edu.hit.demo.model.Users;
import com.edu.hit.demo.service.InviteService;
import com.edu.hit.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;
@Controller
@SessionAttributes({"homeUser", "matchedUsers","inviters","myInvitees","viewUser","invitee"})
public class AcceptController {
    @Autowired
    private UserService userService;
    @Autowired
    private InviteService inviteService;
    @GetMapping("/acceptInvite")
    private String acceptInvitation(@ModelAttribute("homeUser") Users hUser,
                                    @ModelAttribute("matchedUsers") List<Users> matchedUsers,
                                    @ModelAttribute("inviters")List<Users> inviters,
                                    @ModelAttribute("myInvitees") List<Users> myInvitees,
                                    @RequestParam("inviterEmail")String email,
                                    Model model){
        System.out.println(hUser.getEmail());
        System.out.println(email);
        Users inviter = userService.getUserByEmail(email);
        for (Invitee invitee :inviter.getInvitees()){
            if(invitee.getInvitee().getEmail().equals(hUser.getEmail())){
                System.out.println("found");
                invitee.setResponse(1);
                inviteService.onlySaveInvitee(invitee);
            }
        }

        //model.addAttribute("homeUser", hUser);
        model.addAttribute("matchedUsers", matchedUsers);
        model.addAttribute("inviters",inviters);
        model.addAttribute("myInvitees",myInvitees);
        return "sendInviteSuc";

    }
}
