package com.example.demofinal.web.model.dto;

import com.example.demofinal.web.model.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserConverter {
    public UserDTO convertToUserDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setLastname(user.getLastname());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setAge(user.getAge());
        return userDTO;
    }
    public List<UserDTO> convertAllDTO(List<User> userList){
        return userList.stream().map(this::convertToUserDTO).collect(Collectors.toList());
    }
    public User convertToUser(UserDTO userDTO){
        User user = new User();
        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setLastname(userDTO.getLastname());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setAge(userDTO.getAge());
        return user;
    }


        public List<User> convertAllUser(List<UserDTO> userList){
        return userList.stream().map(this::convertToUser).collect(Collectors.toList());
    }
}
