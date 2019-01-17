package com.karengin.libproject.config;

import com.vaadin.spring.annotation.SpringComponent;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.context.annotation.ApplicationScope;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@SpringComponent
@ApplicationScope
public class RedirectAuthenticationSuccessHandler
        implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication)
            throws IOException, ServletException {
        httpServletResponse.sendRedirect("/lib/rest");
    }
}
