package com.example.demofinal.web.controller;

import com.example.demofinal.web.dao.RoleDao;
import com.example.demofinal.web.Service.UserService;
import com.example.demofinal.web.model.Role;
import com.example.demofinal.web.model.User;
import com.example.demofinal.web.model.dto.UserDTO;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("")
    public String showAllUsers(Model model){
        return "showAll";
    }
}

