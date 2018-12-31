package com.karengin.libproject.service;

import com.karengin.libproject.Entity.UsersEntity;
import com.karengin.libproject.Entity.UsersRoleEntity;
import com.karengin.libproject.MockData;
import com.karengin.libproject.repository.UsersRepository;
import com.karengin.libproject.repository.UsersRoleRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(SpringRunner.class)
public class UserDetailsServiceImplTest {

    UserDetailsServiceImpl userDetailsService;

    @Mock
    UsersRepository usersRepository;

    @Mock
    UsersRoleRepository usersRoleRepository;

    @Before
    public void setUp() {
        userDetailsService = new UserDetailsServiceImpl(usersRepository, usersRoleRepository);
    }

    @Test
    public void loadUserByUsername() {
        UsersEntity usersEntity = MockData.usersEntity();
        UsersRoleEntity usersRoleEntity = MockData.usersRoleEntity();

        Mockito.when(usersRepository.findByLogin(usersEntity.getLogin())).thenReturn(usersEntity);
        Mockito.when(usersRoleRepository.findById(usersEntity.getUser_role().getId())).thenReturn(usersRoleEntity);

        UserDetails userDetails = userDetailsService.loadUserByUsername(usersEntity.getLogin());

        assertNotNull(userDetails);
        assertEquals(userDetails.getUsername(), usersEntity.getLogin());
        assertEquals(userDetails.getPassword(), usersEntity.getPassword());
        verify(usersRepository, times(1)).findByLogin(usersEntity.getLogin());
        verify(usersRoleRepository, times(1)).findById(usersRoleEntity.getId());
    }
}
