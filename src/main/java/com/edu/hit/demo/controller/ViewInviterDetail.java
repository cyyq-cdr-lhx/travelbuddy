package com.edu.hit.demo.controller;

import com.edu.hit.demo.model.Interests;
import com.edu.hit.demo.model.Invitee;
import com.edu.hit.demo.model.Users;
import com.edu.hit.demo.service.InterestService;
import com.edu.hit.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;


@Controller
@SessionAttributes({"homeUser", "matchedUsers", "inviters", "myInvitees"})
public class ViewInviterDetail {
    @Autowired
    private UserService userService;
    @Autowired
    private InterestService interestService;

    @GetMapping("/viewInviterDetail/{email}")
    public String viewInviterDetail(@ModelAttribute("homeUser") Users hUser,
                                    @ModelAttribute("matchedUsers") List<Users> matchedUsers,
                                    @ModelAttribute("inviters") List<Invitee> inviters,
                                    @ModelAttribute("myInvitees") List<Invitee> myInvitees,
                                    @PathVariable("email") String vUserEmail, Model model) {

        List<Interests> vUserInterests = interestService.getInterestByEmail(vUserEmail);
        Users vUser = userService.getUserByEmail(vUserEmail);

        model.addAttribute("viewUser", vUser);
        model.addAttribute("vUserInterests", vUserInterests);
        return "viewInviterDetail";
    }
}

