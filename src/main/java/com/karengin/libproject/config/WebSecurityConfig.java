package com.karengin.libproject.config;

import com.karengin.libproject.service.UserDetailsServiceImpl;
import com.vaadin.spring.access.SecuredViewAccessControl;
import com.vaadin.spring.access.ViewAccessControl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImpl userDetailsService;
    private final AuthenticationSuccessHandler successHandler;
    //private final AuthenticationEntryPoint authEntryPoint;

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
//        http.authorizeRequests().antMatchers().permitAll();
//        http.authorizeRequests()
//                .antMatchers("/admin/create_author","/admin/create_book","/admin/delete_comment/{comment_id}")
//                .access("hasRole('ROLE_ADMIN')");
//        http.authorizeRequests().antMatchers("/books/list/{book_id}/add_comment")
//                .access("hasAnyRole('ROLE_ADMIN','ROLE_USER')");
//        http.authorizeRequests().and().httpBasic()
//                .authenticationEntryPoint(authEntryPoint)
//                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests()
            .antMatchers("/VAADIN/**", "/vaadinServlet/**", "/**")
            .hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
            .and()
            .formLogin().successHandler(successHandler).permitAll()
            .and()
            .logout();
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(encoder());
        return authProvider;
    }

    @Bean
    public ViewAccessControl accessControl() {
        return new SecuredViewAccessControl();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(4);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authProvider());
    }
}
