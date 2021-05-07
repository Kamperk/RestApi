package com.example.demofinal.web.model.DTO;

import org.springframework.stereotype.Component;


@Component
public class UserDTO {

    private long id;

    private String name;

    private String lastname;

    private String password;

    private Byte age;

    private String email;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Byte getAge() {
        return age;
    }

    public void setAge(Byte age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
