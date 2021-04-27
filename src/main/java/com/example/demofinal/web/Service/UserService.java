package com.example.demofinal.web.Service;

import com.example.demofinal.web.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface UserService extends UserDetailsService {
    User getUser(long id);
    List<User> getAll();
    void editUser(User user);
    void saveUser(User user);
    void removeUser(long id);
    User findByUsername(String login);
    Optional<User> findById(long id);
}