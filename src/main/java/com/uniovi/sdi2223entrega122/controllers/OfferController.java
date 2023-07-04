package com.uniovi.sdi2223entrega122.controllers;

import com.uniovi.sdi2223entrega122.Logger;
import com.uniovi.sdi2223entrega122.entities.Offer;
import com.uniovi.sdi2223entrega122.entities.User;
import com.uniovi.sdi2223entrega122.services.OffersService;
import com.uniovi.sdi2223entrega122.services.UsersService;
import com.uniovi.sdi2223entrega122.validators.AddOfferValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@Controller
public class OfferController {

    @Autowired
    private OffersService offersService;
    @Autowired
    private UsersService usersService;

    @Autowired
    private AddOfferValidator addOfferValidator;

    @Autowired
    private Logger logger;

    @RequestMapping("/offer/list")
    public String getList(Model model, Principal principal) {
        String email = principal.getName(); // Email es el name de la autenticación
        User user = usersService.getUserByEmail(email);
        List<Offer> offers = offersService.getHighlightedOffers();
        offers.addAll(offersService.getOffersForUserNotHighligted(user));
        model.addAttribute("offersList", offers);
        model.addAttribute("user", user);

        String description = "Method: GET, Mapping: /offer/list, User: " + user.getEmail();
        logger.log("PET", Timestamp.from(Instant.now()), description);

        return "offer/list";
    }

    @RequestMapping("/offer/list/update")
    public String updateList(Model model, Principal principal) {
        String email = principal.getName(); // email es el name de la autenticación
        User user = usersService.getUserByEmail(email);
        List<Offer> offers = offersService.getHighlightedOffers();
        offers.addAll(offersService.getOffersForUserNotHighligted(user));
        model.addAttribute("offersList", offers);
        model.addAttribute("user", user);

        String description = "Method: GET, Mapping: /offer/list/update, User: " + user.getEmail();
        logger.log("PET", Timestamp.from(Instant.now()), description);

        return "offer/list :: offersTable";
    }

    @RequestMapping("/offer/listSearch")
    public String searchList(Model model, Pageable pageable, Principal principal, @RequestParam(required = false) String searchText) {
        String email = principal.getName(); // email es el name de la autenticación
        User user = usersService.getUserByEmail(email);
        Page<Offer> offers = new PageImpl<Offer>(new LinkedList<Offer>());
        if (searchText != null && !searchText.isEmpty()) {
            offers = offersService.searchOffersByTitle(pageable, searchText);
        } else {
            offers = offersService.getOffers(pageable);
        }
        model.addAttribute("searchOffersList", offers.getContent());
        model.addAttribute("page", offers);
        model.addAttribute("user", user);
        model.addAttribute("searchText", searchText);

        String description = "Method: GET, Mapping: /offer/listSearch, User: " + user.getEmail() + ", SearchText: " + searchText;
        logger.log("PET", Timestamp.from(Instant.now()), description);

        return "offer/listSearch";
    }

    @RequestMapping("/offer/listSearch/update")
    public String updateSearchList(Model model, Pageable pageable, Principal principal) {
        String email = principal.getName(); // email es el name de la autenticación
        User user = usersService.getUserByEmail(email);
        Page<Offer> offers = offersService.getOffers(pageable);
        model.addAttribute("searchOffersList", offers.getContent());
        model.addAttribute("user", user);

        String description = "Method: GET, Mapping: /offer/listSearch/update, User: " + user.getEmail();
        logger.log("PET", Timestamp.from(Instant.now()), description);

        return "offer/listSearch :: tableSearchOffers";
    }

    @RequestMapping(value = "/offer/available/{id}", method = RequestMethod.GET)
    public String setAvailableTrue(@PathVariable Long id, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User user = usersService.getUserByEmail(email);
        model.addAttribute("user", user);
        offersService.setOfferAvailable(true, id);

        String description = "Method: GET, Mapping: /offer/available/" + id + ", User: " + user.getEmail();
        logger.log("PET", Timestamp.from(Instant.now()), description);

        return "redirect:/offer/listSearch";
    }

    @RequestMapping(value = "/offer/buy/{id}", method = RequestMethod.GET)
    public String setAvailableFalse(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User user = usersService.getUserByEmail(email);

        Offer offer = offersService.getOffers(id);

        String description = "Method: GET, Mapping: /offer/buy/" + id + ", User: " + user.getEmail();

        if (!offer.isAvailable()) {
            description += ", Error: Offer not available";
            logger.log("PET", Timestamp.from(Instant.now()), description);
            return "redirect:/error";
        }
        int error = offersService.checkErrors(offer, user);
        if (error == 3) {
            description += ", Error: User has already bought this offer";
            redirectAttributes.addFlashAttribute("error3", true);

        } else if (error == 1) {
            description += ", Error 1";
            redirectAttributes.addFlashAttribute("error1", true);
        } else if (error == 2) {
            description += ", Error 2";
            redirectAttributes.addFlashAttribute("error2", true);
        } else {
            offersService.setOfferAvailable(false, id);
            offersService.buyOffer(id, user);
        }

        logger.log("PET", Timestamp.from(Instant.now()), description);

        return "redirect:/offer/listSearch";
    }

    @RequestMapping(value = "/offer/listPurchased", method = RequestMethod.GET)
    public String getPurchasedOffers(Model model, Principal principal) {
        String email = principal.getName();
        User user = usersService.getUserByEmail(email);
        model.addAttribute("purchasedOffersList", offersService.getPurchasedOffers(user));

        String description = "Method: GET, Mapping: /offer/listPurchased, User: " + user.getEmail();
        logger.log("PET", Timestamp.from(Instant.now()), description);

        return "offer/listPurchased";
    }

    @RequestMapping(value = "/offer/add", method = RequestMethod.GET)
    public String getOffer(Model model) {
        model.addAttribute("offer", new Offer());

        String description = "Method: GET, Mapping: /offer/add";
        logger.log("PET", Timestamp.from(Instant.now()), description);

        return "offer/add";
    }

    @RequestMapping(value = "/offer/add", method = RequestMethod.POST)
    public String setOffer(@Validated Offer offer, BindingResult result, @RequestParam(value = "checkbox", required = false) String checkbox) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User user = usersService.getUserByEmail(email);
        offer.setUser(user);
        offer.setDate(LocalDate.now());
        offer.setAvailable(true);
        if (checkbox != null) {
            offer.setHighlighted(true);
        }

        String description = "Method: POST, Mapping: /offer/add, User: " + user.getEmail();

        addOfferValidator.validate(offer, result);
        if (result.hasErrors()) {
            description += ", Error: oferta no valida";
            logger.log("PET", Timestamp.from(Instant.now()), description);
            return "offer/add";
        }

        if (offer.isHighlighted()) {
            usersService.updateWallet(user, -20);
        }

        description += ", Offer: " + offer.getTitle();

        offersService.addOffer(offer);

        logger.log("PET", Timestamp.from(Instant.now()), description);

        return "redirect:/offer/list";
    }

    @RequestMapping(value = "/offer/delete/{id}")
    public String deleteOffer(@PathVariable Long id) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        String description = "Method: GET, Mapping: /offer/delete/" + id + ", User: " + email;

        if (!offersService.getOffers(id).getUser().getEmail().equals(email)) {
            description += ", Error: User not authorized";
            logger.log("PET", Timestamp.from(Instant.now()), description);
            return "redirect:/error";
        } else {
            offersService.deleteOffer(id);
        }

        logger.log("PET", Timestamp.from(Instant.now()), description);

        return "redirect:/offer/list";
    }

    @RequestMapping(value = "/offer/highlight/{id}")
    public String setHighlight(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        User user = usersService.getUserByEmail(email);

        Offer offer = offersService.getOffers(id);

        String description = "Method: GET, Mapping: /offer/highlight/" + id + ", User: " + user.getEmail();

        if (user.getWallet() < 20) {
            redirectAttributes.addFlashAttribute("error1", true);
            return "redirect:/offer/list";
        }

        if (offer.isHighlighted()) {
            redirectAttributes.addFlashAttribute("error2", true);
            return "redirect:/offer/list";
        }

        offer.setHighlighted(true);
        offersService.updateOffer(offer);
        usersService.updateWallet(user, -20);

        logger.log("PET", Timestamp.from(Instant.now()), description);

        return "redirect:/offer/list";
    }

    /**
     * @RequestMapping("/user/details/{id}")
     *     public String getDetail(Model model, @PathVariable Long id) {
     *         model.addAttribute("user", usersService.getUser(id));
     *         return "user/details";
     *     }
     *
     *  @RequestMapping(value = "/user/edit/{id}")
     *     public String getEdit(Model model, @PathVariable Long id) {
     *         User user = usersService.getUser(id);
     *         model.addAttribute("user", user);
     *         return "user/edit";
     *     }
     *
     *     @RequestMapping(value = "/user/edit/{id}", method = RequestMethod.POST)
     *     public String setEdit(@PathVariable Long id, @ModelAttribute User user) {
     *         user.setId(id);
     *         usersService.updateUser(user);
     *         return "redirect:/user/details/" + id;
     *     }
     */
}
