package com.example.demofinal.web.Service;

import com.example.demofinal.web.model.User;
import com.example.demofinal.web.model.dto.UserDTO;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService extends UserDetailsService {
    User getUser(long id);
    List<UserDTO> getAll();
    void editUser(UserDTO user);
    void saveUser(UserDTO user);
    void removeUser(long id);
    User findByUsername(String login);
    UserDTO findById(long id);
}