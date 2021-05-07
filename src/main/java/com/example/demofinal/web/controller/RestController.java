package com.example.demofinal.web.controller;

import com.example.demofinal.web.DAO.RoleDao;
import com.example.demofinal.web.Service.UserService;
import com.example.demofinal.web.model.DTO.UserConverter;
import com.example.demofinal.web.model.DTO.UserDTO;
import com.example.demofinal.web.model.Role;
import com.example.demofinal.web.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
public class RestController {

    private final UserService userService;
    private final RoleDao roleDao;
    private final UserConverter userConverter;


    public RestController(UserService userService, RoleDao roleDao, UserConverter userConverter) {
        this.userService = userService;
        this.roleDao = roleDao;
        this.userConverter = userConverter;
    }

    @GetMapping("")
    public ResponseEntity<List<User>> users(){
        return ResponseEntity.ok().body(userService.getAll());
    }

    @GetMapping("/{id}/show")
    public Optional<User> showUser(@PathVariable(value = "id") long id){
        return userService.findById(id);
    }

    @PostMapping("/create")
    public User addUser(@RequestBody UserDTO userDTO, @RequestParam(value = "roles", required = false) Long [] rolesId){
        User user = userConverter.convertToUser(userDTO);
        List<Role> list = new ArrayList<>();
        for(int i = 0; i<rolesId.length; i++){
            if(rolesId[i]==1){
                list.add(roleDao.findByName("ROLE_ADMIN"));
            }
            if(rolesId[i]==2){
                list.add(roleDao.findByName("ROLE_USER"));
            }
        }
        user.setRoles(list);
        userService.saveUser(user);
        return user;
    }

    @PutMapping("/update")
    public ResponseEntity<User> editUser(@RequestBody UserDTO userDTO, @RequestParam(value = "roles", required = false) Long [] rolesId){
        User user = userConverter.convertToUser(userDTO);
        List<Role> list = new ArrayList<>();
        for(int i = 0; i<rolesId.length; i++){
            if(rolesId[i]==1){
                list.add(roleDao.findByName("ROLE_ADMIN"));
            }
            if(rolesId[i]==2){
                list.add(roleDao.findByName("ROLE_USER"));
            }
        }
        user.setRoles(list);
        userService.editUser(user);
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

