package com.edu.hit.demo.controller;


import com.edu.hit.demo.model.Interests;
import com.edu.hit.demo.model.Users;
import com.edu.hit.demo.service.InterestService;
import com.edu.hit.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SessionAttributes({"homeUser","viewUser","matchedUsers","inviters","myInvitees"})
@Controller
public class UserDetailController {
    @Autowired
    private UserService userService;

    @Autowired
    private InterestService interestService;

    @GetMapping("/viewUserDetail/{userEmail}")
    public String viewUserDetail(@ModelAttribute("homeUser") Users hUser,
                                 @ModelAttribute("matchedUsers")List<Users> matchedUsers,
                                 @ModelAttribute("inviters")List<Users> inviters,
                                 @ModelAttribute("myInvitees") List<Users> myInvitees,
                                 @PathVariable("userEmail") String vUserEmail, Model model){
        System.out.println(vUserEmail);
        System.out.println("homeUser:"+hUser.getEmail());
        model.addAttribute("homeUser",hUser);
        List<Interests> vUserInterests = interestService.getInterestByEmail(vUserEmail);
        Users vUser = userService.getUserByEmail(vUserEmail);
        model.addAttribute("viewUser",vUser);
        model.addAttribute("inviters",inviters);
        model.addAttribute("myInvitees",myInvitees);
        model.addAttribute("matchedUsers",matchedUsers);
        model.addAttribute("vUserInterests", vUserInterests);
        return "viewUserDetail";
    }


}
