package com.edu.hit.demo.controller;

import com.edu.hit.demo.model.Users;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;
@SessionAttributes({"homeUser", "matchedUsers","inviters","myInvitees","viewUser","invitee"})
public class AcceptController {
    @GetMapping("/acceptInvite")
    private String acceptInvitation(@ModelAttribute("homeUser") Users hUser,
                                    @ModelAttribute("matchedUsers") List<Users> matchedUsers,
                                    @ModelAttribute("inviters")List<Users> inviters,
                                    @ModelAttribute("myInvitees") List<Users> myInvitees,
                                    @ModelAttribute("viewUser")Users vUser,
                                    Model model){
        System.out.println(hUser.getEmail());
        System.out.println(vUser.getEmail());




        model.addAttribute("homeUser", hUser);
        model.addAttribute("matchedUsers", matchedUsers);
        model.addAttribute("inviters",inviters);
        model.addAttribute("myInvitees",myInvitees);
        return "sendInviteSuc";

    }
}
