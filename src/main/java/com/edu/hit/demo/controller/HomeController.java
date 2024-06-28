package com.edu.hit.demo.controller;

import com.edu.hit.demo.model.SignUser;
import com.edu.hit.demo.model.Users;
import com.edu.hit.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@SessionAttributes("homeUser")
@Controller
public class HomeController {
    @Autowired
    private UserService userService;


    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("message", "Welcome to Travel Buddy!");
        return "index";
    }

    @GetMapping("/login")
    public String gotoLoginPage(Model model){
        model.addAttribute("user", new Users()); // 添加一个空的 Users 对象到模型中
        model.addAttribute("loginError", ""); // 设置登录错误信息为空字符串
        return "login";
    }

    @GetMapping("/signup")
    public String gotoSignUp(Model model){
        model.addAttribute("signUser", new SignUser());
        model.addAttribute("signupError","");
        return "signUp";
    }

    @PostMapping("/homePage")
    public String loginCheck(@ModelAttribute Users user, Model model){

        Users findUser = userService.getUserByEmail(user.getEmail());
        if (findUser != null) {
            if (findUser.getPassword().equals(user.getPassword())) {
                model.addAttribute("homeUser", findUser);
                return "homePage";
            } else {
                model.addAttribute("loginError", "Password is not correct.");
                model.addAttribute("user", user); // Add user back to model
                return "login";
            }
        } else {
            model.addAttribute("loginError", "Account not found, please sign up.");
            model.addAttribute("user", user); // Add user back to model
            return "login";
        }
    }


    @GetMapping("/homePage")
    public String homePage(Model model) {
        // Add any necessary attributes to the model

        model.addAttribute("homeUser", new Users());
        return "homePage";  // Return the name of your home page view
    }

    @GetMapping("/backToHomePage")
    public String backToHomePage(@ModelAttribute("homeUser") Users hUser,Model model){
        model.addAttribute("homeUser",hUser);
        return "homePage";
    }



    @PostMapping("/signUpSuc")
    public String SignUpCheck(@ModelAttribute SignUser signUser, Model model){
        Users findUser = userService.getUserByEmail(signUser.getEmail());
        if(findUser != null){
            model.addAttribute("signupError", "user email "+signUser.getEmail()+" already exist, please login");
            model.addAttribute("signUser",signUser);
            return "signUp";

        }
        else{
            //看两次设置的密码是否相同
            if (signUser.getPassword1().equals(signUser.getPassword2())){
                Users newUser = new Users();
                newUser.setEmail(signUser.getEmail());
                newUser.setPassword(signUser.getPassword1());
                newUser.setUsername(signUser.getUsername());
                userService.saveUser(newUser);
                model.addAttribute("homeUser", newUser);
                return "redirect:/homePage";
            }
            else{
                model.addAttribute("signupError","The password is inconsistent, please re-enter it");
                model.addAttribute("signUser" ,signUser);
                return "signUp";

            }
        }
    }

    @GetMapping("/routePlan")
    public String showRoutePlan() {
        return "routePlan";  // 返回名为 'route.html' 的视图
    }





}