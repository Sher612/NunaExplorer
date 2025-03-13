package com.example.Nuna.service;

import com.example.Nuna.model.AdminUser;
import com.example.Nuna.model.Explorer;
import com.example.Nuna.repository.AdminUserRepository;
import com.example.Nuna.repository.ExplorerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private ExplorerRepository explorerRepository;

    @Autowired
    private AdminUserRepository adminUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //Try to find the user as an Explorer
        Optional<Explorer> explorer = explorerRepository.findByUsername(username);
        if (explorer.isPresent()) {
            return new org.springframework.security.core.userdetails.User(
                    explorer.get().getUsername(),
                    explorer.get().getPassword(),
                    Collections.singleton(new SimpleGrantedAuthority("ROLE_EXPLORER"))
            );
        }

        //Try to find the user as an AdminUser
        Optional<AdminUser> adminUser = adminUserRepository.findByUsername(username);
        if (adminUser.isPresent()) {
            return new org.springframework.security.core.userdetails.User(
                    adminUser.get().getUsername(),
                    adminUser.get().getPassword(),
                    Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN"))
            );
        }

        throw new UsernameNotFoundException("User not found with username: " + username);
    }
}
