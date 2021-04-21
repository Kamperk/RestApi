package com.example.demofinal.web.DAO;

import com.example.demofinal.web.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface RoleDao extends JpaRepository<Role, Long> {
    Role findByName(String name);
}