package com.example.demofinal.web.DAO;

import com.example.demofinal.web.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {
    User getUser(long id);
    List<User> getAll();
    void editUser(User user);
    void saveUser(User user);
    void removeUser(long id);
    public User findByUsername(String login);
}