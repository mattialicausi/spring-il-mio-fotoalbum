package com.java.springilmiofotoalbum.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashSet;
import java.util.Set;

public class DatabaseUserDetails implements UserDetails {

    private String username;
    private String password;

    private Set<GrantedAuthority> authorities;

    public DatabaseUserDetails(User user) {
        this.username = user.getEmail();
        this.password = user.getPassword();
        this.authorities = new HashSet<>();
        for (Role r : user.getRoles()) {
            authorities.add(new SimpleGrantedAuthority(r.getName()));
        }
    }
}
