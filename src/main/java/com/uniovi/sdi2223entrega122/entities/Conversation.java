package com.uniovi.sdi2223entrega122.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Conversation Entity
 */
@Entity
@Table(name = "conversation")
public class Conversation {

    /**
     * Generated ID for each Conversation
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * User/Customer related to the Conversation
     */
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private User customer;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private User seller;


    /**
     * Offer related to the Conversation
     */
    @ManyToOne
    @JoinColumn(name = "offer_id")
    private Offer offer;

    /**
     * Messages sent in this conversation
     */
    @ElementCollection
    @CollectionTable(name = "TMessages")
    private Set<Message> messageSet = new HashSet<Message>();

    /**
     * Default empty constructor for conversation
     */
    public Conversation() {
    }

    /**
     * Constructor for conversation with user and offer as params
     *
     * @param user
     * @param offer
     */
    public Conversation(User user, Offer offer) {
        this.customer = user;
        this.offer = offer;
        this.messageSet = new HashSet<Message>();
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    /**
     * Setter for the conversation id
     *
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Getter for the conversation id
     *
     * @return
     */
    public Long getId() {
        return id;
    }

    /**
     * Getter for the conversation offer
     *
     * @return
     */
    public Offer getOffer() {
        return this.offer;
    }

    /**
     * Setter for the conversation offer
     *
     * @param offer
     */
    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    /**
     * Add a message to the conversation message set
     *
     * @param message
     */
    public void addMessage(Message message) {
        messageSet.add(message);
    }

}
