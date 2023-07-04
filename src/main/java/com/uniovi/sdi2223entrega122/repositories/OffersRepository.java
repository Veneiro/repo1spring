package com.uniovi.sdi2223entrega122.repositories;

import com.uniovi.sdi2223entrega122.entities.Offer;
import com.uniovi.sdi2223entrega122.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OffersRepository extends CrudRepository<Offer, Long> {

    @Query("SELECT r FROM Offer r WHERE r.user = ?1 ORDER BY r.id ASC")
    List<Offer> findAllByUser(User user);

    @Query("SELECT r FROM Offer r WHERE r.user.email = ?1 ORDER BY r.id ASC")
    List<Offer> findAllByUserEmail(String email);

    @Query("Select r From Offer r Where (LOWER(r.title) LIKE LOWER(?1) OR LOWER (r.user.name) LIKE LOWER (?1))")
    Page<Offer> searchByTitle(Pageable pageable, String searchText);

    Page<Offer> findAll(Pageable pageable);

    List<Offer> findAll();

    @Modifying
    @Transactional
    @Query("UPDATE Offer SET available = ?1 WHERE id = ?2")
    void updateAvailable(Boolean available, Long id);

    @Query("SELECT o FROM Offer o WHERE o.userBuyer = ?1")
    List<Offer> findPurchasedOffersByUser(User user);

    @Query("SELECT o FROM Offer o WHERE (o.highlighted = true AND o.available = true)")
    List<Offer> findAllHighlighted();

    @Query("SELECT o FROM Offer o WHERE (o.highlighted = false AND o.available = true AND o.user = ?1)")
    List<Offer> findAllByUserNotHighligted(User user);
}
