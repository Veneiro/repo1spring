package com.uniovi.sdi2223entrega122.interceptors;

import com.uniovi.sdi2223entrega122.Logger;
import com.uniovi.sdi2223entrega122.entities.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;

@Component
public class LogoutHandler implements LogoutSuccessHandler {

    @Autowired
    private Logger logger;

    @Override
    public void onLogoutSuccess(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response, org.springframework.security.core.Authentication authentication) throws java.io.IOException {
        String email = authentication.getName();
        String description = "Method: POST, Mapping: /logout, User: " + email;
        logger.log(new Log("LOGOUT", Timestamp.from(Instant.now()), description));
        response.sendRedirect("/login");
    }
}
