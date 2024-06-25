package com.edu.hit.demo.controller;

import com.edu.hit.demo.model.Interests;
import com.edu.hit.demo.model.Invitee;
import com.edu.hit.demo.model.Users;
import com.edu.hit.demo.service.InterestService;
import com.edu.hit.demo.service.InviteService;
import com.edu.hit.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;

@Controller
@SessionAttributes({"homeUser", "matchedUsers","inviters","myInvitees"})
public class InviteController {
    @Autowired
    private InviteService inviteService;
    @Autowired
    private InterestService interestService;

    @Autowired
    private UserService userService;

    @Transactional
    @GetMapping("/sendInvite")
    public String sendInvite(@ModelAttribute("homeUser") Users hUser,
                             @ModelAttribute("matchedUsers") List<Users> matchedUsers,
                             @ModelAttribute("inviters")List<Users> inviters,
                             @ModelAttribute("myInvitees") List<Users> myInvitees,
                             @RequestParam("tarUserEmail") String tarUserEmail, Model model) {
        Users tarUser = userService.getUserByEmail(tarUserEmail);

        // 检查是否已经存在邀请关系
        for (Invitee aInvite : hUser.getInvitees()){
            if(aInvite.getInvitee().getEmail().equals(tarUserEmail)){
                // 如果已经存在邀请关系，可以根据需要采取其他操作或返回错误信息
                model.addAttribute("error", "Invite already exists between these users.");
                model.addAttribute("homeUser",hUser);
                model.addAttribute("viewUser",tarUser);
                model.addAttribute("matchedUsers",matchedUsers);
                List<Interests> vUserInterests = interestService.getInterestByEmail(tarUserEmail);
                model.addAttribute("vUserInterests", vUserInterests);
                model.addAttribute("inviters",inviters);
                model.addAttribute("myInvitees",myInvitees);

                return "viewUserDetail"; // 返回一个错误页面或其他处理逻辑
            }
        }


        //Invite thInvite;
        Invitee newInvitee = new Invitee();
        newInvitee.setInvitee(tarUser);
        newInvitee.setResponse(0);
        newInvitee.setInviter(hUser);
        newInvitee.setInvitee(tarUser);

        //保存invitee
        inviteService.onlySaveInvitee(newInvitee);


        List<Invitee> thInvitees = hUser.getInvitees();
        thInvitees.add(newInvitee);
        hUser.setInvitees(thInvitees);
        userService.updateUser(hUser);




        model.addAttribute("homeUser", hUser);
        model.addAttribute("matchedUsers", matchedUsers);
        model.addAttribute("inviters",inviters);
        model.addAttribute("myInvitees",myInvitees);

        return "sendInviteSuc";
    }
}
