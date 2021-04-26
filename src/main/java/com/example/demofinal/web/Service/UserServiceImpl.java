package com.example.demofinal.web.Service;

import com.example.demofinal.web.DAO.UserDao;
import com.example.demofinal.web.model.Role;
import com.example.demofinal.web.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDao userDao;


    @Override
    public User getUser(long id) {
        return userDao.getOne(id);
    }

    @Override
    public List<User> getAll() {
        return userDao.findAll();
    }

    @Override
    public void editUser(User user) {
        String crypto = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(crypto);
        userDao.saveAndFlush(user);
    }

    @Override
    public void saveUser(User user) {
        if (userDao.findByEmail(user.getEmail()) == null){
        String crypto = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(crypto);
        userDao.save(user);}

    }

    @Override
    public User findByUsername(String login) {
        return userDao.findByEmail(login);
    }

    @Override
    public void removeUser(long id ) {
        userDao.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userDao.findByEmail(username);

        if(user==null){
            throw new UsernameNotFoundException(String.format("Пользователь с именем %s не найден", username));
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getAuthorities());
    }
}