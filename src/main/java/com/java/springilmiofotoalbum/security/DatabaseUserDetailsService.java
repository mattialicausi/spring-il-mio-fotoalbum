package com.java.springilmiofotoalbum.security;

import com.java.springilmiofotoalbum.model.User;
import com.java.springilmiofotoalbum.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class DatabaseUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(username);
        if (user.isPresent()) {
            // creo un oggetto UserDetails a partire da quello User
            return new DatabaseUserDetails(user.get());
        } else {
            throw new UsernameNotFoundException("User with email " + username + " not found");
        }
    }
}
