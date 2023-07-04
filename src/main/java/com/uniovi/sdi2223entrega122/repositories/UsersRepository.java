package com.uniovi.sdi2223entrega122.repositories;

import com.uniovi.sdi2223entrega122.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UsersRepository extends CrudRepository<User, Long> {

    User findByEmail(String email);

    @Query("SELECT u.password FROM User u WHERE u.email = ?1")
    User findPasswordByEmail(String email);
}
