package com.uniovi.sdi2223entrega122.validators;

import com.uniovi.sdi2223entrega122.entities.User;
import com.uniovi.sdi2223entrega122.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class SignUpFormValidator implements Validator {
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
        // El email tiene que ser único.
        if (usersService.getUserByEmail(user.getEmail()) != null) {
            errors.rejectValue("email", "Error.signup.email.duplicate");
        }
        // Nombre vacío
        if (user.getName().length() < 3 || user.getName().length() > 24) {
            errors.rejectValue("name", "Error.signup.name.length");
        }
        // Apellidos vacíos
        if (user.getLastName().length() < 3 || user.getLastName().length() > 24) {
            errors.rejectValue("lastName", "Error.signup.lastName.length");
        }
        // Contraseña entre 5 y 24 caracteres
        if (user.getPassword().length() < 4 || user.getPassword().length() > 24) {
            errors.rejectValue("password", "Error.signup.password.length");
        }
        if (!user.getPasswordConfirm().equals(user.getPassword())) {
            errors.rejectValue("passwordConfirm",
                    "Error.signup.passwordConfirm.coincidence");
        }

    }
}
