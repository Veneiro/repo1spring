package com.uniovi.sdi2223entrega122.services;

import com.uniovi.sdi2223entrega122.entities.Conversation;
import com.uniovi.sdi2223entrega122.entities.Message;
import com.uniovi.sdi2223entrega122.entities.Offer;
import com.uniovi.sdi2223entrega122.entities.User;
import com.uniovi.sdi2223entrega122.repositories.ConversationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Service for Conversations
 */
@Service
public class ConversationService {

    /**
     * Autowired - ConversationsRepository
     */
    @Autowired
    private ConversationsRepository conversationsRepository;

    /**
     * Method that gives all the conversations in the repository ordered by pages
     *
     * @param pageable
     * @return A Set with all the conversations ordered in pages
     */
    public Set<Conversation> getConversations(Pageable pageable) {
        return conversationsRepository.findAll(pageable);
    }

    /**
     * Gives us a conversation by a given id
     *
     * @param id
     * @return A conversation with the same id as the one we give
     */
    public Conversation getConversation(Long id) {
        return conversationsRepository.findById(id).get();
    }

    /**
     * Add a conversation to the database
     *
     * @param conversation
     */
    public void addConversation(Conversation conversation) {
        conversationsRepository.save(conversation);
    }

    /**
     * Delete a conversation from the database
     *
     * @param id
     */
    public void deleteConversation(Long id) {
        conversationsRepository.deleteById(id);
    }

    /**
     * Returns the conversations for a specified offer
     *
     * @param offer
     * @return
     */
    public Set<Conversation> getConversationsForOffer(Offer offer) {
        return offer.getConversations();
    }

    /**
     * Returns the conversations for a specified user
     *
     * @param pageable
     * @param customer
     * @return
     */
    public Page<Conversation> getConversationsByUser(Pageable pageable, User customer) {
        Page<Conversation> conversations = new PageImpl<>(new LinkedList<Conversation>());
        conversations = conversationsRepository.findAllByUser(pageable, customer);
        return conversations;
    }

    /**
     * Returns the messages for and specific conversation id
     *
     * @param id
     * @return
     */
    public List<Message> getMessageOfAConversation(Long id) {
        List<Message> messages = new ArrayList<Message>();
        conversationsRepository.findMessageById(id).forEach(messages::add);
        return messages;
    }

    /**
     * Returns the conversations for a specific user and offer
     *
     * @param user
     * @param offer
     * @return
     */
    public Conversation getConversationsByUserAndOffer(User user, Offer offer) {
        Conversation conversation;
        conversation = conversationsRepository.findByUserAndOffer(user, offer);
        if (conversation == null) {
            conversation = new Conversation(user, offer);
            conversationsRepository.save(conversation);
        }
        return conversation;
    }

    public Conversation getById(Long id) {
        return conversationsRepository.findById(id).get();
    }
}
