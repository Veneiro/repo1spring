package com.uniovi.sdi2223entrega122.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Embeddable
public class Message {

    /**
     * User propietary of the message
     */
    @ManyToOne
    @JoinColumn(name = "usermessage_id")
    private User user;

    /**
     * Text inside the message
     */
    private String messageText;

    /**
     * Date when the message was sent
     */
    private LocalDate date;

    /**
     * Time when the message was sent
     */
    private LocalTime time;

    /**
     * Default empty constructor
     */
    public Message() {
    }

    /**
     * Constructor that allows to create a message with customizable values for the
     * variables, this constructor set the time and date to the one when the message
     * was created with the .now() method
     *
     * @param user        User propietary of the message
     * @param messageText Text of the message
     */
    public Message(User user, String messageText) {
        this.user = user;
        this.messageText = messageText;
        this.date = LocalDate.now();
        this.time = LocalTime.now();
    }

    public User getUser() {
        return this.user;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public LocalTime getTime() {
        return this.time;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }
}
