package com.example.demofinal.web.controller;

import com.example.demofinal.web.Service.UserService;
import com.example.demofinal.web.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("")
    public String showUser(Model model, Principal principal){
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user",user);
        return "showUser";
    }

    @GetMapping("/authorizedUser")
    @ResponseBody
    public ResponseEntity<User> getAuthorizedUser(){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(userService.findByUsername(userDetails.getUsername()));
    }

}