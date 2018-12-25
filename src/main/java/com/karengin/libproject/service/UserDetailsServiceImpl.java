package com.karengin.libproject.service;

import com.karengin.libproject.repository.UsersRepository;
import com.karengin.libproject.repository.UsersRoleRepository;
import com.karengin.libproject.Entity.UsersEntity;
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
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UsersRepository usersRepository;
    private final UsersRoleRepository usersRoleRepository;


    @Autowired
    public UserDetailsServiceImpl(final UsersRepository usersRepository, final UsersRoleRepository usersRoleRepository) {
        this.usersRepository = usersRepository;
        this.usersRoleRepository = usersRoleRepository;
    };

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {

        UsersEntity user = usersRepository.findByLogin(login);
        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority("ROLE_" + usersRoleRepository.findById(user.getId()).getRole()));

        UserDetails userDetails =
                new org.springframework.security.core.userdetails.User(user.getLogin(),
                        user.getPassword(),
                        roles);

        return userDetails;
    }
}
