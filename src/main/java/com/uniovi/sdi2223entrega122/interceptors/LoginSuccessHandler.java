package com.uniovi.sdi2223entrega122.interceptors;

import com.uniovi.sdi2223entrega122.Logger;
import com.uniovi.sdi2223entrega122.entities.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private Logger logger;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        String description = "Method: POST, Mapping: /login, User: " + email;
        logger.log(new Log("LOGIN-EX", Timestamp.from(Instant.now()), description));

        response.sendRedirect("/home");
    }
}
