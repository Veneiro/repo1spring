package com.uniovi.sdi2223entrega122.controllers;


import com.uniovi.sdi2223entrega122.Logger;
import com.uniovi.sdi2223entrega122.entities.Conversation;
import com.uniovi.sdi2223entrega122.entities.Message;
import com.uniovi.sdi2223entrega122.entities.Offer;
import com.uniovi.sdi2223entrega122.entities.User;
import com.uniovi.sdi2223entrega122.services.ConversationService;
import com.uniovi.sdi2223entrega122.services.OffersService;
import com.uniovi.sdi2223entrega122.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

/**
 * Controller for all the conversations in the wallapop app
 */
@Controller
public class ConversationController {

    /**
     * Conversation Service
     */
    @Autowired
    private ConversationService conversationService;

    /**
     * Offers Service
     */
    @Autowired
    private OffersService offersService;

    /**
     * Users Service
     */
    @Autowired
    private UsersService usersService;

    /**
     * Logger
     */
    @Autowired
    private Logger logger;

    /**
     * Add the list of all the conversations related to the user to the html model and allow the user to enter on each
     * of them
     * @param model
     * @param pageable
     * @param principal
     * @return
     */
    @RequestMapping("/conversations/list")
    public String getList(Model model, Pageable pageable, Principal principal) {
        User customer = usersService.getUserByEmail(principal.getName());
        Page<Conversation> conversations = conversationService.getConversationsByUser(pageable, customer);
        model.addAttribute("conversationsList", conversations.getContent());
        model.addAttribute("page", conversations);

        String description = "Method: GET, Mapping: conversations/list, User: " + principal.getName();
        logger.log("PET", Timestamp.from(Instant.now()), description);

        return "conversations/list";
    }

    /**
     * Functionality for the Inside Conversation view, allows to send messages to that conversation and add them to the
     * database
     *
     * @param model
     * @param message
     * @return
     */
    @RequestMapping(value = "/conversations/add/{id}")
    public String addConversation(Model model, @PathVariable Long id,
                                  @RequestParam(required = false) String message) {
        //Get User authentication
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //Get the email from the authentication
        String email = authentication.getName();

        //Getting the information I need from the services
        User user = usersService.getUserByEmail(email);
        Offer offer = offersService.getOffers(id);
        Conversation conversation = conversationService.getConversationsByUserAndOffer(user, offer);
        conversation.setSeller(offer.getUserSeller());
        // Checking if the message I try to send is valid and if it is adding it to the conversation
        if (message != null && !message.isEmpty()) {
            Message m = new Message(user, message);
            conversation.addMessage(m);
            conversationService.addConversation(conversation);
        }

        // Updating the html model
        model.addAttribute("messages", conversationService.getMessageOfAConversation(conversation.getId()));
        model.addAttribute("offer", "/conversations/add/" + offer.getId());
        model.addAttribute("conversationId", conversation.getId());
        model.addAttribute("user", user.getId());

        String description = "Method: GET, Mapping: conversation/add, User: " + user.getEmail();
        logger.log("PET", Timestamp.from(Instant.now()), description);

        return "conversations/add";
    }

    @RequestMapping(value = "/conversations/get/add/{id}")
    public String getConversation(Model model, @PathVariable Long id,
                                  @RequestParam(required = false) String message) {
        //Get User authentication
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //Get the email from the authentication
        String email = authentication.getName();

        //Getting the information I need from the services
        User user = usersService.getUserByEmail(email);
        Conversation conversation = conversationService.getById(id);
        Offer offer = conversation.getOffer();
        // Checking if the message I try to send is valid and if it is adding it to the conversation
        if (message != null && !message.isEmpty()) {
            Message m = new Message(user, message);
            conversation.addMessage(m);
            conversationService.addConversation(conversation);
        }

        // Updating the html model
        model.addAttribute("messages", conversationService.getMessageOfAConversation(conversation.getId()));
        model.addAttribute("offer", "/conversations/add/" + offer.getId());
        model.addAttribute("conversationId", conversation.getId());
        model.addAttribute("user", user.getId());

        String description = "Method: GET, Mapping: conversation/add, User: " + user.getEmail();
        logger.log("PET", Timestamp.from(Instant.now()), description);

        return "conversations/add";
    }

    @RequestMapping("/conversation/add/{id}/update")
    public String updateList(Model model, @PathVariable Long id) {
        //Get User authentication
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //Get the email from the authentication
        String email = authentication.getName();

        //Getting the information I need from the services
        User user = usersService.getUserByEmail(email);
        Conversation c = conversationService.getConversation(id);
        List<Message> messages = conversationService.getMessageOfAConversation(c.getId());
        model.addAttribute("messages", messages);
        model.addAttribute("user", user.getId());

        String description = "Method: GET, Mapping: conversation/add/update, User: " + user.getEmail();
        logger.log("PET", Timestamp.from(Instant.now()), description);

        return "conversations/add :: tableConversations";
    }

    @RequestMapping("/conversation/delete/{id}")
    public String deleteConverstion(@PathVariable Long id) {
        conversationService.deleteConversation(id);
        return "redirect:/conversations/list";
    }
}
