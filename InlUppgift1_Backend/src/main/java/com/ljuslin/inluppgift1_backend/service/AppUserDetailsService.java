package com.ljuslin.inluppgift1_backend.service;

import com.ljuslin.inluppgift1_backend.repository.AppUserRepository;
import com.ljuslin.inluppgift1_backend.security.AppUser;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailsService implements UserDetailsService {
    private final AppUserRepository repo;

    public AppUserDetailsService(AppUserRepository repo) {
        this.repo = repo;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser u = repo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found : " + username));
        var auth = new SimpleGrantedAuthority("ROLE_" + u.getRole());

        return User.withUsername(u.getUsername())
                .password(u.getPassword())
                .authorities(auth)
                .build();
    }
}
