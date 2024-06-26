package com.edu.hit.demo.controller;


import com.edu.hit.demo.model.Users;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes({"homeUser","viewUser"})


public class testController {
    @GetMapping("/test")
    public String test(@ModelAttribute("homeUser") Users hUser,
                       @ModelAttribute("viewUser")Users vUser, Model model){
        System.out.println(hUser.getEmail());
        System.out.println(vUser.getEmail());
        model.addAttribute("homeUser",hUser);
        model.addAttribute("viewUser",vUser);
        return "sendInviteSuc";
    }
}
