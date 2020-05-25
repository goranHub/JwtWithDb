package com.example.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

import lombok.Setter;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Component
@JsonIgnoreProperties({"authorities"})

public class User implements UserDetails, Serializable {



    private int id;
  //  @Column("login")
    private String login;
    private String password;
    private String fname;
    private String lname;
    private String email;
    private UserDetails usserdetails;
    String role;
    private int active;
    String ROLE_PREFIX = "ROLE_";


    public User(String subject, String password, Collection<? extends GrantedAuthority> authorities) {
        super();
    }

    public User() {
    }

    public User(int id, String login, String password, String fname, String lname, String email) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
    }

    public User(String username, String password, String role) {
        this.fname = username;
        this.password = password;
        this.role = role;
    }

    public User(String username, String password) {
        this.fname = username;
        this.password = password;
    }


    public User(int id, String fname, String lname) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> list = new ArrayList<GrantedAuthority>();
        list.add(new SimpleGrantedAuthority(ROLE_PREFIX + role));
        return list;
    }


    @Override
    public String getUsername() {
        return getFname();
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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", fname='" + fname + '\'' +
                ", lname='" + lname + '\'' +
                ", email='" + email + '\'' +
                ", usserdetails=" + usserdetails +
                ", role='" + role + '\'' +
                ", active=" + active +
                ", ROLE_PREFIX='" + ROLE_PREFIX + '\'' +
                '}';
    }
}