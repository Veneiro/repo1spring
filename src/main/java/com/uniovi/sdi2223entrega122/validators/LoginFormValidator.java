package com.uniovi.sdi2223entrega122.validators;

import com.uniovi.sdi2223entrega122.entities.User;
import com.uniovi.sdi2223entrega122.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class LoginFormValidator implements Validator {
    @Autowired
    private UsersService usersService;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        // Email vacío
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "Error.empty");
        // Contraseña vacía
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "Error.empty");
        // Email no existe
        if (usersService.getUserByEmail(user.getEmail()) == null) {
            errors.rejectValue("email", "Error.login.email");
        }
        // Contraseña incorrecta
        if (usersService.getPasswordByEmail(user.getEmail()) == null) {
            errors.rejectValue("password", "Error.login.password");
        }

    }
}
