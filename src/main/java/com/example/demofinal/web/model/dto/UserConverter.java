package com.example.demofinal.web.model.dto;

import com.example.demofinal.web.model.Role;
import com.example.demofinal.web.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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
        String [] roles = new String[2];
        for(int i=0; i<user.getRolesList().size(); i++){
            roles[i] = user.getRolesList().get(i).getName().substring(5);
        }
        userDTO.setRoles(roles);
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
        List<Role> roles = new ArrayList<>();
        for(int i = 0; i<userDTO.getRoles().length; i++){
            if(userDTO.getRoles()[i].contains("ADMIN")){
                roles.add(new Role(1, "ROLE_ADMIN"));
            }else{
                roles.add(new Role(2, "ROLE_USER"));
            }
        }
        user.setRoles(roles);
        return user;
    }


        public List<User> convertAllUser(List<UserDTO> userList){
        return userList.stream().map(this::convertToUser).collect(Collectors.toList());
    }
}
