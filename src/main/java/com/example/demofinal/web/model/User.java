package com.example.demofinal.web.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name="users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;


    @Column(name = "name")
    private String name;

    @Column(name = "lastname")
    private String lastname;

    @Column(name = "job")
    private String job;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @Column
    private Byte age;

    @Column
    private String email;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
    joinColumns = @JoinColumn(name = "user_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;

    @Transient
    private String[] rolesArray;

    public User() {
    }

    public User(String name, String lastname, String job) {
        this.name = name;
        this.lastname = lastname;
        this.job = job;
    }

    public User(long id, String name, String lastname, String job) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.job = job;
    }

    public User(long id, String name, String lastname, String job, String username, String password, Byte age, String email, List<Role> roles, String[] rolesArray) {
        this.id=id;
        this.name = name;
        this.lastname = lastname;
        this.job = job;
        this.username = username;
        this.password = password;
        this.age = age;
        this.email = email;
        this.roles = roles;
        this.rolesArray = rolesArray;
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

    public String getJob() {
        return job;
    }

    public void setJob(String job){
        this.job = job;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoles() {
        return roles.toString().replace("[", "").replace("]", "");
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String[] getRolesArray() {
        return rolesArray;
    }

    public void setRolesArray(String[] rolesArray) {
        this.rolesArray = rolesArray;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

