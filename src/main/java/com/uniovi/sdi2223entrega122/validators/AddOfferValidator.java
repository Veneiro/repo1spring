package com.uniovi.sdi2223entrega122.validators;

import com.uniovi.sdi2223entrega122.entities.Offer;
import com.uniovi.sdi2223entrega122.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class AddOfferValidator implements Validator {

    @Autowired
    private UsersService usersService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Offer.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Offer offer = (Offer) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "Error.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "detail", "Error.empty");
        if (offer.getTitle().length() < 3 || offer.getTitle().length() > 20) {
            errors.rejectValue("title", "Error.offer.title.length");
        }
        if (offer.getDetail().length() > 280) {
            errors.rejectValue("detail", "Error.offer.detail.length");
        }
        if (offer.getPrice() <= 0) {
            errors.rejectValue("price", "Error.offer.price");
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (offer.isHighlighted() && usersService.getUserByEmail(auth.getName()).getWallet() < 20) {
            errors.rejectValue("user", "Error.offer.wallet");
        }
    }
}
