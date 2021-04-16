package com.example.demofinal.web.controller;

import com.example.demofinal.web.DAO.RoleDao;
import com.example.demofinal.web.Service.UserService;
import com.example.demofinal.web.model.Role;
import com.example.demofinal.web.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
        model.addAttribute("allUsers", userService.getAll());
        return "showAll";
    }
    @GetMapping("/{id}/show")
    public String showUser(Model model, @PathVariable(value = "id") long id){
        model.addAttribute("user", userService.getUser(id));
        return "showUser";
    }

    @GetMapping("/new")
    public String newUser(Model model){
        model.addAttribute("user", new User());
   //     String [] roles = new String[]{"ADMIN", "USER"};
        model.addAttribute("rolesList",roleDao.getAll());
        return "newUser";
    }
    @PostMapping("")
    public String addUser(@ModelAttribute("user") User user){
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/{id}/edit")
    public String editUser(@PathVariable("id") long id, Model model){
        model.addAttribute("user", userService.getUser(id));
        model.addAttribute("rolesList",roleDao.getAll());
        return "editUser";
    }

    @PatchMapping("/{id}")
    public String editUser(@ModelAttribute("user") User user, @ModelAttribute("rolesList") String [] roleList){
        List<Role> list =new ArrayList<>();
        if(roleList!=null){
        for(int i = 0; i<roleList.length; i++){
            if(roleList[i].equals("ADMIN")){
                list.add(roleDao.getByName("ROLE_ADMIN"));
            }
            if(roleList[i].equals("USER")){
                list.add(roleDao.getByName("ROLE_USER"));
            }
        }
        user.setRoles(list);}
        userService.editUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/{id}")
    public String removeUser(@PathVariable("id") long id){
        userService.removeUser(id);
        return "redirect:/admin";
    }
}

