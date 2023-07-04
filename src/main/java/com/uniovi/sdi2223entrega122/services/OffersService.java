package com.uniovi.sdi2223entrega122.services;

import com.uniovi.sdi2223entrega122.entities.Offer;
import com.uniovi.sdi2223entrega122.entities.User;
import com.uniovi.sdi2223entrega122.repositories.OffersRepository;
import com.uniovi.sdi2223entrega122.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.LinkedList;
import java.util.List;

@Service
public class OffersService {

    @Autowired
    private OffersRepository offersRepository;

    @Autowired
    private UsersRepository usersRepository;

    @PostConstruct
    public void init() {
    }

    public Page<Offer> getOffers(Pageable pageable) {
        Page<Offer> offers = new PageImpl<Offer>(new LinkedList<Offer>());
        offers = offersRepository.findAll(pageable);
        return offers;
    }

    public List<Offer> getOffers() {
        List<Offer> offers = new LinkedList<Offer>();
        offers = offersRepository.findAll();
        return offers;
    }

    public List<Offer> getOffersForUser(User user) {
        List<Offer> offers = new LinkedList<Offer>();
        if (user.getRole().equals("ROLE_STANDARD")) {
            offers = offersRepository.findAllByUser(user);
        }
        return offers;
    }

    public List<Offer> getOffersForUserNotHighligted(User user) {
        List<Offer> offers = new LinkedList<Offer>();
        if (user.getRole().equals("ROLE_STANDARD")) {
            offers = offersRepository.findAllByUserNotHighligted(user);
        }
        return offers;
    }

    public List<Offer> getOffersForUserEmail(String userEmail) {
        return offersRepository.findAllByUserEmail(userEmail);
    }

    public Page<Offer> searchOffersByTitle(Pageable pageable, String searchText) {
        Page<Offer> offers = new PageImpl<Offer>(new LinkedList<Offer>());
        searchText = "%" + searchText + "%";
        offers = offersRepository.searchByTitle(pageable, searchText);
        return offers;
    }

    public void setOfferAvailable(boolean revised, Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        Offer offer = offersRepository.findById(id).get();
        if (!offer.getUser().getEmail().equals(email)) {
            offersRepository.updateAvailable(revised, id);
        }
    }

    public void addOffer(Offer offer) {
        offersRepository.save(offer);
    }

    public Offer getOffers(Long id) {
        return offersRepository.findById(id).get();
    }

    public void deleteOffer(Long id) {
        offersRepository.deleteById(id);
    }

    public void buyOffer(Long id, User user) {
        Offer offer = offersRepository.findById(id).get();
        if (offer.isAvailable() && user.getWallet() >= offer.getPrice()) {
            offer.setUserBuyer(user);
            offer.setAvailable(false);
            user.setWallet(user.getWallet() - offer.getPrice());
            usersRepository.save(user);
            offersRepository.save(offer);
        }
    }

    public int checkErrors(Offer offer, User user) {
        if (offer.getUser() == user) {
            return 3;
        } else if (user.getWallet() < offer.getPrice()) {
            return 1;
        } else if (!offer.isAvailable()) {
            return 2;
        } else {
            return 0;
        }
    }

    public List<Offer> getPurchasedOffers(User user) {
        return offersRepository.findPurchasedOffersByUser(user);
    }

    public List<Offer> getHighlightedOffers() {
        return offersRepository.findAllHighlighted();
    }

    public void updateOffer(Offer offer) {
        offersRepository.save(offer);
    }

    /**
     * public void updateUser(User user) {
     *         User userInRepository = getUser(user.getId());
     *         userInRepository.setDni(user.getDni());
     *         userInRepository.setName(user.getName());
     *         userInRepository.setLastName(user.getLastName());
     *         usersRepository.save(userInRepository);
     *
     *     }
     */
}
