package com.example.springsecurityjwt.service;

import com.example.springsecurityjwt.model.User;
import com.example.springsecurityjwt.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class UsersService implements UserDetailsService {

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = usersRepository.findByUsername(s);
        if(user != null){
            return new org.springframework.security.core.userdetails.User(s, user.getPassword(),getAuthorities(s));
        }
        throw new UsernameNotFoundException(s+" is not found.");
    }

    public Collection<? extends GrantedAuthority> getAuthorities(String s) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        User user = usersRepository.findByUsername(s);
        user.getRoles().forEach(p -> {
            GrantedAuthority authority = new SimpleGrantedAuthority(p.getName());
            authorities.add(authority);
        });
        /*
        users.getRoles().forEach(r -> {
            GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" +r.getName());
            authorities.add(authority);
        });
        */
        return authorities;
    }



}
