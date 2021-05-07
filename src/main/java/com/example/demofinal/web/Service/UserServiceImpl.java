package com.example.demofinal.web.Service;

import com.example.demofinal.web.dao.RoleDao;
import com.example.demofinal.web.dao.UserDao;
import com.example.demofinal.web.model.Role;
import com.example.demofinal.web.model.User;
import com.example.demofinal.web.model.dto.UserConverter;
import com.example.demofinal.web.model.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService{

    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private UserConverter userConverter;


    @Override
    public User getUser(long id) {
        return userDao.getOne(id);
    }

    @Override
    public List<User> getAll() {
        return userDao.findAll();
    }

    @Override
    public User editUser(UserDTO userDTO, Long [] rolesId) {
        User user = userConverter.convertToUser(userDTO);
        String crypto = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(crypto);
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
        userDao.saveAndFlush(user);
        return user;
    }

    @Override
    public User saveUser(UserDTO userDTO, Long [] rolesId) {
        User user = userConverter.convertToUser(userDTO);
        if (userDao.findByEmail(user.getEmail()) == null){
        String crypto = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(crypto);
        userDao.save(user);
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
        }
    return user;
    }

    @Override
    public User findByUsername(String login) {
        return userDao.findByEmail(login);
    }

    @Override
    public void removeUser(long id ) {
        userDao.deleteById(id);
    }

    public Optional<User> findById(long id) {
        return userDao.findById(id);
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