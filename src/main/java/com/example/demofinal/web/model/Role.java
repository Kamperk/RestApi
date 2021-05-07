package com.example.demofinal.web.model;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {

    @Id
    @Column(name = "Id")
    private long id;

    @Column
    private String name;

    @ManyToMany(mappedBy = "roles")
    private List<User> users;


    public Role() {
    }

    public Role(String name) {
        if(name.contains("ADMIN")){
            this.id= 1;
        }if(name.contains("USER")){
            this.id= 2;
        }
        this.name = name;
    }

    public Role(long id) {
        this.id = id;
    }

    public Role(long id, String role) {
        this.id = id;
        this.name = role;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String role) {
        this.name = role;
    }

    @Override
    public String getAuthority() {
        return name;
    }

    @Override
    public String toString() {
        return name.substring(5);
    }
}
