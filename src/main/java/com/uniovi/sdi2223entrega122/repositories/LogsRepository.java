package com.uniovi.sdi2223entrega122.repositories;

import com.uniovi.sdi2223entrega122.entities.Log;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface LogsRepository extends CrudRepository<Log, Long> {

    @Query("SELECT l FROM Log l ORDER BY l.timestamp DESC")
    List<Log> findAll();

    @Query("SELECT l FROM Log l WHERE LOWER(l.type) LIKE LOWER(?1) ORDER BY l.timestamp DESC")
    List<Log> findAllByType(String type);

}
