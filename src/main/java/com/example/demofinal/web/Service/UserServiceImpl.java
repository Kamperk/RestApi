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
    public List<UserDTO> getAll() {
        return userConverter.convertAllDTO(userDao.findAll());
    }

    @Override
    public void editUser(UserDTO userDTO) {
        User user = userConverter.convertToUser(userDTO);
        String crypto = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(crypto);
        List<Role> list = new ArrayList<>();
        for(int i = 0; i<userDTO.getRoles().length; i++){
            if(userDTO.getRoles()[i].contains("ADMIN")){
                list.add(roleDao.findByName("ROLE_ADMIN"));
            }
            if(userDTO.getRoles()[i].contains("USER")){
                list.add(roleDao.findByName("ROLE_USER"));
            }
        }
        user.setRoles(list);
        userDao.saveAndFlush(user);
    }

    @Override
    public void saveUser(UserDTO userDTO) {
        User user = userConverter.convertToUser(userDTO);
        if (userDao.findByEmail(user.getEmail()) == null){
        String crypto = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(crypto);
        userDao.save(user);
            List<Role> list = new ArrayList<>();
            for(int i = 0; i<userDTO.getRoles().length; i++){
                if(userDTO.getRoles()[i].contains("ADMIN")){
                    list.add(roleDao.findByName("ROLE_ADMIN"));
                }
                if(userDTO.getRoles()[i].contains("USER")){
                    list.add(roleDao.findByName("ROLE_USER"));
                }
            }
            user.setRoles(list);
        }
    }

    @Override
    public User findByUsername(String login) {
        return userDao.findByEmail(login);
    }

    @Override
    public void removeUser(long id ) {
        userDao.deleteById(id);
    }

    public UserDTO findById(long id) {
        UserDTO userDTO = userConverter.convertToUserDTO(userDao.findById(id).get());
        return userDTO;
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