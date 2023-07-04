package com.uniovi.sdi2223entrega122.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "offer")
public class Offer {
    @Id
    @GeneratedValue
    private Long id;

    private String title;
    private String detail;
    private long price;
    private boolean available = true;

    // Tarea opcional 18
    private boolean highlighted = false;

    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private User userBuyer;

    @OneToMany(mappedBy = "offer", cascade = CascadeType.ALL)
    private Set<Conversation> conversations;

    public Offer() {
    }

    public Offer(Long id, String title, String detail, long price, boolean available, boolean highlighted, User user, User userBuyer) {
        this.id = id;
        this.title = title;
        this.detail = detail;
        this.price = price;
        this.available = available;
        this.highlighted = highlighted;
        this.user = user;
        this.userBuyer = userBuyer;
    }

    public Offer(String title, String detail, long price, boolean available, boolean highlighted, User user, User userBuyer) {
        this.title = title;
        this.detail = detail;
        this.price = price;
        this.available = available;
        this.highlighted = highlighted;
        this.user = user;
        this.userBuyer = userBuyer;
    }

    public Offer(String title, String detail, long price, User user, User userBuyer) {
        this.title = title;
        this.detail = detail;
        this.price = price;
        this.user = user;
        this.userBuyer = userBuyer;
    }

    public Offer(String title, String detail, long price, User user) {
        this.title = title;
        this.detail = detail;
        this.price = price;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public boolean isHighlighted() {
        return highlighted;
    }

    public void setHighlighted(boolean highlighted) {
        this.highlighted = highlighted;
    }

    public User getUserSeller() {
        return user;
    }

    public void setUserSeller(User userSeller) {
        this.user = userSeller;
    }

    public User getUserBuyer() {
        return userBuyer;
    }

    public void setUserBuyer(User userBuyer) {
        this.userBuyer = userBuyer;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Set<Conversation> getConversations() {
        return conversations;
    }

    public void setConversations(Set<Conversation> conversations) {
        this.conversations = conversations;
    }

    @Override
    public String toString() {
        return "Offer{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", detail='" + detail + '\'' +
                ", price=" + price +
                ", available=" + available +
                ", highlighted=" + highlighted +
                ", date=" + date +
                ", user=" + user +
                ", userBuyer=" + userBuyer +
                ", conversations=" + conversations +
                '}';
    }
}
