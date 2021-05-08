package com.example.demofinal.web.controller;

import com.example.demofinal.web.dao.RoleDao;
import com.example.demofinal.web.Service.UserService;
import com.example.demofinal.web.model.dto.UserDTO;
import com.example.demofinal.web.model.Role;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<List<UserDTO>> users(){
        return ResponseEntity.ok().body(userService.getAll());
    }

    @GetMapping("/{id}/show")
    public ResponseEntity<UserDTO> showUser(@PathVariable(value = "id") long id){
        UserDTO userDTO = userService.findById(id);
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping("/create")
    public ResponseEntity<UserDTO> addUser(@RequestBody UserDTO userDTO){
        userService.saveUser(userDTO);
        return ResponseEntity.ok().body(userDTO);
    }

    @PutMapping("/update")
    public ResponseEntity<UserDTO> editUser(@RequestBody UserDTO userDTO){
        userService.editUser(userDTO);
        return ResponseEntity.ok().body(userDTO);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> removeUser(@PathVariable("id") long id) {
        userService.removeUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/getUserRole/{id}")
    public ResponseEntity<Optional<Role>> getRole(@PathVariable("id") Long id){
        return ResponseEntity.ok().body(roleDao.findById(id));
    }
}

