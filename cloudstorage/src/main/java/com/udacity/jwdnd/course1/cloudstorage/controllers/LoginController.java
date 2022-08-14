package com.udacity.jwdnd.course1.cloudstorage.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/login")
public class LoginController {

    @GetMapping()
    public String login(@RequestParam(value = "error", required = false) boolean error, @RequestParam(value = "isLogout", required = false) boolean isLogout, Model model){
        model.addAttribute("error", "false");
        if(error){
            model.addAttribute("error", "true");
        }
        if(isLogout){
            model.addAttribute("isLogout", "true");
        }
        return "login";
    }
}
