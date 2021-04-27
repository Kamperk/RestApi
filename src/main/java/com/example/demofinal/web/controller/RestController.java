package com.example.demofinal.web.controller;

import com.example.demofinal.web.DAO.RoleDao;
import com.example.demofinal.web.Service.UserService;
import com.example.demofinal.web.model.Role;
import com.example.demofinal.web.model.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
public class RestController {

    private final UserService userService;

    private final RoleDao roleDao;

    public RestController(UserService userService, RoleDao roleDao) {
        this.userService = userService;
        this.roleDao = roleDao;
    }

    @GetMapping("")
    public List<User> users(){
        return userService.getAll();
    }

    @GetMapping("/{id}/show")
    public Optional<User> showUser(Model model, @PathVariable(value = "id") long id){
        return userService.findById(id);
    }

    @PostMapping("/create")
    public User addUser(@RequestBody User user){
        userService.saveUser(user);
        return user;
    }

    @PutMapping("/update")
    public User editUser(@RequestBody User user){
        userService.editUser(user);
        return user;
    }

    @DeleteMapping("/delete/{id}")
    public String removeUser(@PathVariable("id") long id){
        Optional<User> user = userService.findById(id);
        if(!user.isPresent()){
            return "There is not user with ID " + id + " in Database";
        }
        userService.removeUser(id);
        return "User with ID " + id + " was deleted";
    }
}

