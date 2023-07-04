package com.uniovi.sdi2223entrega122.controllers;

import com.uniovi.sdi2223entrega122.Logger;
import com.uniovi.sdi2223entrega122.entities.User;
import com.uniovi.sdi2223entrega122.services.OffersService;
import com.uniovi.sdi2223entrega122.services.RolesService;
import com.uniovi.sdi2223entrega122.services.SecurityService;
import com.uniovi.sdi2223entrega122.services.UsersService;
import com.uniovi.sdi2223entrega122.validators.SignUpFormValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Timestamp;
import java.time.Instant;

@Controller
public class UsersController {

    @Autowired
    private UsersService usersService;

    @Autowired
    private RolesService rolesService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private SignUpFormValidator signUpFormValidator;

    @Autowired
    private OffersService offersService;

    @Autowired
    private Logger logger;

    @RequestMapping("/user/list")
    public String getListado(Model model) {
        model.addAttribute("usersList", usersService.getUsers());

        String description = "Method: GET, Mapping: /user/list";
        logger.log("PET", Timestamp.from(Instant.now()), description);

        return "user/list";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.GET)
    public String signup(Model model) {
        model.addAttribute("user", new User());

        String description = "Method: GET, Mapping: /signup";
        logger.log("PET", Timestamp.from(Instant.now()), description);

        return "signup";
    }

    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public String signup(@Validated User user, BindingResult result) {
        signUpFormValidator.validate(user, result);

        String description = "Method: POST, Mapping: /signup, User: " + user.getEmail();

        if (result.hasErrors()) {
            description += ", Error: " + result.getAllErrors().get(0).getDefaultMessage();
            logger.log("PET", Timestamp.from(Instant.now()), description);
            return "signup";
        }
        user.setRole(rolesService.getRoles()[0]);
        usersService.addUser(user);
        securityService.autoLogin(user.getEmail(), user.getPasswordConfirm());

        logger.log("ALTA", Timestamp.from(Instant.now()), description);

        return "redirect:home";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        String description = "Method: GET, Mapping: /login";
        logger.log("PET", Timestamp.from(Instant.now()), description);

        return "login";
    }

    @RequestMapping(value = {"/home"}, method = RequestMethod.GET)
    public String home(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User activeUser = usersService.getUserByEmail(email);

        String description = "Method: GET, Mapping: /home, User: " + email;

        if (activeUser.getRole().equals(rolesService.getRoles()[1])) {
            description += ", Role: " + activeUser.getRole();
            logger.log("PET", Timestamp.from(Instant.now()), description);
            model.addAttribute("usersList", usersService.getUsers());
            return "redirect:/user/list";
        } else {
            description += ", Role: " + activeUser.getRole();
            logger.log("PET", Timestamp.from(Instant.now()), description);
            model.addAttribute("offersList", offersService.getOffersForUser(activeUser));
            return "redirect:/offer/list";
        }
    }

    @RequestMapping(value = "/user/delete", method = RequestMethod.POST)
    public String deleteUser(@RequestParam(value = "checkboxUser", required = false) String[] checkboxValue) {
        // Comprobar que si es el email de admin, redirija a otra vista
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String emailLogeado = auth.getName();
        User activeUser = usersService.getUserByEmail(emailLogeado);
        String adminEmail = "admin@email.com";

        StringBuilder description = new StringBuilder("Method: POST, Mapping: /user/delete, User: " + emailLogeado);
        logger.log("PET", Timestamp.from(Instant.now()), description.toString());

        if (activeUser.getRole().equals(rolesService.getRoles()[0])) {
            description.append(", Error: No tiene permisos para eliminar usuarios");
            logger.log("PET", Timestamp.from(Instant.now()), description.toString());
            return "redirect:/error";
        }

        for (String email : checkboxValue) {
            if (email.equals(adminEmail)) {
                description.append(", Error: No se puede eliminar el usuario admin");
                logger.log("PET", Timestamp.from(Instant.now()), description.toString());
                return "redirect:/error";
            }

            description.append(", User: ").append(email);
            usersService.deleteUser(usersService.getUserByEmail(email).getId());
        }

        logger.log("PET", Timestamp.from(Instant.now()), description.toString());
        return "redirect:/user/list";
    }
}
