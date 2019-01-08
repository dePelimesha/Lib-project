package com.karengin.libproject.service;

import com.karengin.libproject.repository.UsersRepository;
import com.karengin.libproject.repository.UsersRoleRepository;
import com.karengin.libproject.Entity.UsersEntity;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        UsersEntity user = usersRepository.findByLogin(login);
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(user.getUserRole().getRole()));

        return new org.springframework.security.core.userdetails.User(user.getLogin(),
                user.getPassword(),
                roles);
    }
}
