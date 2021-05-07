package com.example.demofinal.web.controller;

import com.example.demofinal.web.dao.RoleDao;
import com.example.demofinal.web.Service.UserService;
import com.example.demofinal.web.model.dto.UserConverter;
import com.example.demofinal.web.model.dto.UserDTO;
import com.example.demofinal.web.model.Role;
import com.example.demofinal.web.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<List<User>> users(){
        return ResponseEntity.ok().body(userService.getAll());
    }

    @GetMapping("/{id}/show")
    public ResponseEntity<Optional<User>> showUser(@PathVariable(value = "id") long id){
        return ResponseEntity.ok(userService.findById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<User> addUser(@RequestBody UserDTO userDTO, @RequestParam(value = "roles", required = false) Long [] rolesId){
        User user = userService.saveUser(userDTO, rolesId);
        return ResponseEntity.ok().body(user);
    }

    @PutMapping("/update")
    public ResponseEntity<User> editUser(@RequestBody UserDTO userDTO, @RequestParam(value = "roles", required = false) Long [] rolesId){
        User user = userService.editUser(userDTO, rolesId);
        return ResponseEntity.ok().body(user);
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

    @GetMapping("/getUserRole/{id}")
    public ResponseEntity<Optional<Role>> getRole(@PathVariable("id") Long id){
        return ResponseEntity.ok().body(roleDao.findById(id));
    }
}

