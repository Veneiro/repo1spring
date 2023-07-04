package com.uniovi.sdi2223entrega122.repositories;

import com.uniovi.sdi2223entrega122.entities.Conversation;
import com.uniovi.sdi2223entrega122.entities.Message;
import com.uniovi.sdi2223entrega122.entities.Offer;
import com.uniovi.sdi2223entrega122.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface ConversationsRepository extends CrudRepository<Conversation, Long> {

    /**
     * Get all conversations
     *
     * @param pageable
     * @return a Set of Conversation objects
     */
    Set<Conversation> findAll(Pageable pageable);

    /**
     * Get conversations by offer
     *
     * @param offer
     * @return
     */
    Iterable<Conversation> findByOffer(Offer offer);

    /**
     * Get All conversations by user
     *
     * @param pageable
     * @param customer
     * @return
     */
    @Query("SELECT r FROM Conversation r WHERE r.customer = ?1 OR r.seller = ?1 ORDER BY r.id ASC")
    Page<Conversation> findAllByUser(Pageable pageable, User customer);

    /**
     * Get a conversation by User and Offer
     *
     * @param user
     * @param offer
     * @return
     */
    @Query("SELECT c FROM Conversation c WHERE (c.customer = ?1 or c.offer.user = ?1) and c.offer = ?2")
    Conversation findByUserAndOffer(User user, Offer offer);

    /**
     * Get messages by Conversation id
     *
     * @param id
     * @return
     */
    @Query("SELECT m FROM Conversation c inner join c.messageSet m WHERE c.id = ?1 ORDER BY m.time")
    Iterable<Message> findMessageById(Long id);

}
