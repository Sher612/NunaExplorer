package com.example.Nuna.service;

import com.example.Nuna.model.AdminUser;
import com.example.Nuna.model.Explorer;
import com.example.Nuna.repository.AdminUserRepository;
import com.example.Nuna.repository.ExplorerRepository;
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
        System.out.println("Attempting login for username: " + username);
        //Try to find the user as an Explorer
        Optional<Explorer> explorer = explorerRepository.findByUsername(username);
        if (explorer.isPresent()) {
            System.out.println("Found Explorer" + explorer.get().getUsername());
            return new org.springframework.security.core.userdetails.User(
                    explorer.get().getUsername(),
                    explorer.get().getPassword(),
                    Collections.singleton(new SimpleGrantedAuthority("ROLE_EXPLORER"))
            );
        }

        //Try to find the user as an AdminUser
        Optional<AdminUser> adminUser = adminUserRepository.findByUsername(username);
        if (adminUser.isPresent()) {
            System.out.println("Found AdminUser" + adminUser.get().getUsername());
            return new org.springframework.security.core.userdetails.User(
                    adminUser.get().getUsername(),
                    adminUser.get().getPassword(),
                    Collections.singleton(new SimpleGrantedAuthority("ROLE_ADMIN"))
            );
        }

        System.out.println("User Not Found" + username);
        throw new UsernameNotFoundException("User not found with username: " + username);
    }
}
