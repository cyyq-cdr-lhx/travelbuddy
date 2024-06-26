package com.edu.hit.demo.controller;

import com.edu.hit.demo.model.Interests;
import com.edu.hit.demo.model.SignUser;
import com.edu.hit.demo.model.Users;
import com.edu.hit.demo.service.InterestService;
import com.edu.hit.demo.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;


@Controller
@SessionAttributes("homeUser")

public class HomeController {
    @Autowired
    private UserService userService;

    private final InterestService interestService;

    @Autowired
    public HomeController(InterestService interestService) {
        this.interestService = interestService;
    }

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

    public String homePage(@ModelAttribute("homeUser") Users homeUser, Model model) {
       // model.addAttribute("homeUser", new Users());
        model.addAttribute("homeUser", homeUser);

        return "homePage";  // Return the name of your home page view
    }

    public String backToHomePage(@ModelAttribute("homeUser") Users hUser,Model model){
        model.addAttribute("homeUser",hUser);
        return "homePage";
    }



    @PostMapping("/signUpSuc")
    public String SignUpCheck(@ModelAttribute SignUser signUser, HttpSession session, Model model){
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
                //设置会话属性
                //session.setAttribute("homeUser",newUser);
                return "redirect:/homePage";
            }
            else{
                model.addAttribute("signupError","The password is inconsistent, please re-enter it");
                model.addAttribute("signUser" ,signUser);
                return "signUp";

            }
        }
    }

    @GetMapping("/profile")
    public String Profile(@ModelAttribute("homeUser") Users hUser,Model model) {
        model.addAttribute("homeUser",hUser);
        model.addAttribute("originalEmail", hUser.getEmail());
        return "profile";
    }

    @PostMapping("/updateProfile")
    public String updateProfile(@ModelAttribute("homeUser") Users homeUser, @RequestParam("originalEmail") String originalEmail, Model model) {
       // Users originalUser = userService.getUserById(hUser.getId());
       // Users updateUser = new Users();

        // edit email
        //System.out.println("originalEmail:"+originalEmail);
        if (!homeUser.getEmail().equals(originalEmail)) {
            Users findUser = userService.getUserByEmail(homeUser.getEmail());
            if(findUser!= null) {
                model.addAttribute("updateError","This email already exists!");
                //return "redirect:/profile";
                return "profile";
            }

           //updateUser.setEmail(homeUser.getEmail());
        }

        //userService.updateUser(updateUser);
        userService.updateUser(homeUser);
        model.addAttribute("homeUser", homeUser);
        model.addAttribute("updateError","");
        return "redirect:/profile"; // Redirect to avoid form resubmission
    }

    @GetMapping("/travelInterests")
    public String toTravelInterests(@ModelAttribute("homeUser") Users hUser, Model model) {
        //System.out.println("userEmail:"+hUser.getEmail());
        Interests userInterest = interestService.getInterestByEmail(hUser.getEmail());

        if(userInterest==null){
            model.addAttribute("userInterest",new Interests());
            model.addAttribute("userEmail", hUser.getEmail());
            return "travelInterests";
        }
        model.addAttribute("userInterest",userInterest);
        model.addAttribute("userEmail", hUser.getEmail());
        return "travelInterests";
    }
    @PostMapping("/travelInterests")
    public String getTravelInterests(@ModelAttribute("homeUser") Users hUser,@ModelAttribute Interests userInterest, @RequestParam("userEmail") String email, Model model) {
        userInterest.setEmail(email);
        interestService.saveInterest(userInterest);
        model.addAttribute("homeUser",hUser);
        model.addAttribute("userInterest",userInterest);
        model.addAttribute("userEmail", userInterest.getEmail());
        return "redirect:/travelInterests";
    }
}
