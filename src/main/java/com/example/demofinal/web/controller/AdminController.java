package com.example.demofinal.web.controller;

import com.example.demofinal.web.DAO.RoleDao;
import com.example.demofinal.web.Service.UserService;
import com.example.demofinal.web.model.Role;
import com.example.demofinal.web.model.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    private final RoleDao roleDao;

    public AdminController(UserService userService, RoleDao roleDao) {
        this.userService = userService;
        this.roleDao = roleDao;
    }

    @GetMapping("")
    public String showAllUsers(Model model){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userService.findByUsername(userDetails.getUsername());
        model.addAttribute("allUsers", userService.getAll());
        model.addAttribute("authorizedUser", user);
        model.addAttribute("newUser", new User());
        model.addAttribute("allRoles", roleDao.findAll());
        return "showAll";
    }
    @GetMapping("/{id}/show")
    public String showUser(Model model, @PathVariable(value = "id") long id){
        model.addAttribute("user", userService.getUser(id));
        return "showUser";
    }

    @PostMapping("/create")
    public String addUser(@ModelAttribute("user") User user, @RequestParam(value = "roleList") String [] roleList){
        List<Role> list =new ArrayList<>();
            for(int i = 0; i<roleList.length; i++){
                if(roleList[i].equals("ADMIN")){
                    list.add(roleDao.findByName("ROLE_ADMIN"));
                }
                if(roleList[i].equals("USER")){
                    list.add(roleDao.findByName("ROLE_USER"));
                }
            }
            user.setRoles(list);
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/edit")
    public String editUser(@PathVariable("id") long id, Model model){
        model.addAttribute("user", userService.getUser(id));
        model.addAttribute("rolesList",roleDao.findAll());
        return "editUser";
    }

    @PostMapping("/update")
    public String editUser(@ModelAttribute User user ,@RequestParam(value = "roleList", required = false) String [] roleList ){
        List<Role> list =new ArrayList<>();
        if(roleList!=null){
        for(int i = 0; i<roleList.length; i++){
            if(roleList[i].equals("ADMIN")){
                list.add(roleDao.findByName("ROLE_ADMIN"));
            }
            if(roleList[i].equals("USER")){
                list.add(roleDao.findByName("ROLE_USER"));
            }
        }
        user.setRoles(list);}
        userService.editUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/delete/{id}")
    public String removeUser(@PathVariable("id") long id){
        userService.removeUser(id);
        return "redirect:/admin";
    }
}

