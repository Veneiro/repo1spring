package com.uniovi.sdi2223entrega122.interceptors;

import com.uniovi.sdi2223entrega122.Logger;
import com.uniovi.sdi2223entrega122.entities.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;

@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {

    @Autowired
    private Logger logger;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {

        String description = "Method: POST, Mapping: /login, Error: " + exception.getMessage();
        logger.log(new Log("LOGIN-ERR", Timestamp.from(Instant.now()), description));
        response.sendRedirect("/login?error");
    }
}