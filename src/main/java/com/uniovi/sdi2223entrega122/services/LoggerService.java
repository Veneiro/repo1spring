package com.uniovi.sdi2223entrega122.services;

import com.uniovi.sdi2223entrega122.entities.Log;
import com.uniovi.sdi2223entrega122.repositories.LogsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoggerService {

    @Autowired
    private LogsRepository logRepository;

    public void addLog(Log log) {
        logRepository.save(log);
    }

    public void deleteAllLogs() {
        logRepository.deleteAll();
    }

    public void deleteLog(Long id) {
        logRepository.deleteById(id);
    }

    public List<Log> getAllLogs() {
        return logRepository.findAll();
    }

    public List<Log> getAllLogsByType(String type) {
        return logRepository.findAllByType(type);
    }

}
