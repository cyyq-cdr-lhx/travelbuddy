package com.edu.hit.demo.controller;

import com.edu.hit.demo.model.Users;
import com.edu.hit.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@SessionAttributes("homeUser")
@Controller
public class ProfileController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public String Profile(@ModelAttribute("homeUser") Users hUser, Model model) {
        model.addAttribute("homeUser", hUser);
        model.addAttribute("originalEmail", hUser.getEmail());
        return "profile";
    }

    @PostMapping("/updateProfile")
    public String updateProfile(@ModelAttribute("homeUser") Users hUser, @RequestParam("originalEmail") String originalEmail, Model model) {
        // 如果用户更新了电子邮件地址，则检查新电子邮件是否已存在
        if (!hUser.getEmail().equals(originalEmail)) {
            Users existingUser = userService.getUserByEmail(hUser.getEmail());
            if (existingUser != null) {
                model.addAttribute("updateError", "This email already exists!");
                return "profile";
            }
        }

        // 更新用户信息
        userService.updateUser(hUser);
        model.addAttribute("homeUser", hUser);
        model.addAttribute("updateError", "");

        return "redirect:/profile"; // 重定向以避免表单重复提交
    }
}
