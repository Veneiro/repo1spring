package com.uniovi.sdi2223entrega122.services;

import com.uniovi.sdi2223entrega122.entities.User;
import com.uniovi.sdi2223entrega122.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class UsersService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UsersRepository usersRepository;

    @PostConstruct
    public void init() {
    }

    public void addUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        usersRepository.save(user);
    }

    public User getUserByEmail(String email) {
        return usersRepository.findByEmail(email);
    }

    public User getPasswordByEmail(String email) {
        return usersRepository.findPasswordByEmail(email);
    }

    public List<User> getUsers() {
        List<User> users = new ArrayList<User>();
        usersRepository.findAll().forEach(users::add);
        return users;
    }

    public void deleteUser(Long id) {
        usersRepository.deleteById(id);
    }

    public void deleteAll() {
        usersRepository.deleteAll();
    }

    public void updateWallet(User user, long money) {
        user.setWallet(user.getWallet() + money);
        usersRepository.save(user);
    }
}
