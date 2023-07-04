package com.uniovi.sdi2223entrega122.interceptors;

import com.uniovi.sdi2223entrega122.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class UserInterceptor implements HandlerInterceptor {

    @Autowired
    private UsersService usersService;

    @Override
    public void postHandle(HttpServletRequest req, HttpServletResponse res, Object o, ModelAndView model) {
        String loggedUser = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!loggedUser.equals("anonymousUser") && model != null) {
            model.addObject("email", usersService.getUserByEmail(loggedUser).getEmail());
            model.addObject("wallet", usersService.getUserByEmail(loggedUser).getWallet());
        }
    }
}

