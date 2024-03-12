package com.example.demo.controllers;

import com.example.demo.domain.User;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.HashMap;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/register")
    String test(User user){
        return "register";
    }

    @PostMapping("/register")
    String testpost(@Valid User user,
                    BindingResult result,
                    Model model) {
        if (result.hasErrors()){
            return "register";
        }
        HashMap result_map = userService.checkUser(user);
        if(result_map.get("status").equals(true))
        {
            userService.saveUser(user);
            return "redirect:/login";
        }
        model.addAttribute("error", result_map);
        return "register";
    }

    @GetMapping("/login")
    String test2(){
        return "login2";
    }

}
