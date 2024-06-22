package com.edu.hit.demo.controller;


import com.edu.hit.demo.model.Users;
import com.edu.hit.demo.repository.InterestRepository;
import com.edu.hit.demo.service.InterestService;
import com.edu.hit.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.edu.hit.demo.model.Interests;

import java.time.LocalDate;
import java.util.List;

@SessionAttributes("homeUser")
@Controller
public class InterestController {
    @Autowired
    private InterestRepository interestRepository;

    @Autowired
    private InterestService interestService;

    @Autowired
    private UserService userService;

    @GetMapping("/travelInterests")
    public String toTravelInterests(@ModelAttribute("homeUser") Users hUser, Model model) {
        //System.out.println("userEmail:"+hUser.getEmail());
        List<Interests> userInterests = interestService.getInterestByEmail(hUser.getEmail());
        /*
        if(userInterests==null){
            model.addAttribute("userInterest",new Interests());
            model.addAttribute("userEmail", hUser.getEmail());
            return "travelInterests";
        }

         */
        model.addAttribute("userInterest",new Interests());
        model.addAttribute("userEmail",hUser.getEmail());
        model.addAttribute("userInterests",userInterests);
        model.addAttribute("homeUser",hUser);
        //model.addAttribute("userEmail", hUser.getEmail());


        return "travelInterests";
    }
    @PostMapping("/travelInterests")
    public String getTravelInterests(@ModelAttribute("homeUser") Users hUser,
                                     @ModelAttribute Interests userInterest,
                                     @RequestParam("userEmail") String email, Model model) {
        userInterest.setEmail(email);
        interestService.saveInterest(userInterest);
        model.addAttribute("homeUser",hUser);
        //model.addAttribute("userInterest",userInterest);
        model.addAttribute("userEmail", userInterest.getEmail());

        return "redirect:/travelInterests";
    }
    @GetMapping("/modifyInterest")
    public String afterModifyTravelInterests(
            @RequestParam("id") Integer thId,
            @ModelAttribute("homeUser") Users thUser,
            Model model) {

        Interests thInterest = interestService.getInterestById(thId);


        //Users hUser = userService.getUserByEmail(userEmail); // Assume there's a method to find the user by email

        model.addAttribute("homeUser", thUser);
        model.addAttribute("interest", thInterest);
        //model.addAttribute("userEmail", userEmail);

        return "travelInterestModify";
    }

    @PostMapping("/modifyInterest")
    public String modifyTravelInterest(
            @ModelAttribute("interest") Interests thInterest,
            @ModelAttribute("homeUser") Users hUser,
            Model model) {

        model.addAttribute("homeUser", hUser);
        model.addAttribute("userEmail", hUser.getEmail());
        thInterest.setEmail(hUser.getEmail());
        interestService.updateInterest(thInterest);

        List<Interests> userInterests = interestService.getInterestByEmail(hUser.getEmail());
        model.addAttribute("userInterests",userInterests);
        model.addAttribute("userInterest",new Interests());
        return "travelInterests";
    }

    @GetMapping("/deleteInterest")

    public String deleteInterest (
            @RequestParam("id") Integer thId,
            @ModelAttribute("homeUser") Users thUser,
            Model model
    ){
        System.out.println(thId);
        Interests thInterest = interestService.getInterestById(thId);
        if(thInterest!=null){
            interestService.deleteInterestById(thId);

        }

        List<Interests> userInterests = interestService.getInterestByEmail(thUser.getEmail());
        model.addAttribute("userInterests",userInterests);
        model.addAttribute("userInterest",new Interests());
        return "travelInterests";
    }

}
