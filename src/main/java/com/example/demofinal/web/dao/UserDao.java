package com.example.demofinal.web.dao;

import com.example.demofinal.web.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface UserDao extends JpaRepository<User, Long>{
    User findByEmail(String email);
}