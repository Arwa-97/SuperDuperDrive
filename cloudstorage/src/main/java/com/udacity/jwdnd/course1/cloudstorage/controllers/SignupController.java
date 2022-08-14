package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.UserSignupForm;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class SignupController {
    private UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String viewSignup(){
        return "signup";
    }

    @PostMapping
    public String signup(@ModelAttribute UserSignupForm signupForm, Model model){
        var userId = this.userService.insertUser(signupForm);
        if(userId == 0){
            model.addAttribute("usernameExist", true);
            return "signup";
        }
        return "redirect:/login";
    }
}
