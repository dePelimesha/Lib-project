package com.karengin.libproject.controller;

import com.karengin.libproject.MockData;
import com.karengin.libproject.config.AuthenticationEntryPoint;
import com.karengin.libproject.dto.UsersDto;
import com.karengin.libproject.service.UserDetailsServiceImpl;
import com.karengin.libproject.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static sun.plugin2.util.PojoUtil.toJson;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    UserDetailsServiceImpl userDetailsService;

    @MockBean
    AuthenticationEntryPoint authenticationEntryPoint;

    @MockBean
    UserService userService;

    @Test
    public void register() throws Exception {
        final UsersDto usersDto = MockData.usersDto();

        Mockito.when(userService.register(usersDto)).
                thenReturn(ResponseEntity.status(201).body("Account was created"));

        mockMvc.perform(post("/register").with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(usersDto)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().string("Account was created"));
    }

    @Test
    @WithMockUser(username = "testUser", password = "pass123", roles = "ADMIN")
    public void logout() throws Exception {

        mockMvc.perform(get("/logout").with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("You are logout"));
    }
}
