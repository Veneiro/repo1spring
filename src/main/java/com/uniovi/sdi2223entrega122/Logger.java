package com.uniovi.sdi2223entrega122;

import com.uniovi.sdi2223entrega122.entities.Log;
import com.uniovi.sdi2223entrega122.services.LoggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class Logger {

    @Autowired
    private LoggerService loggerService;

    public Logger() {
    }

    public void log(Log log) {
        loggerService.addLog(log);
    }

    public void log(String type, Timestamp timestamp, String description) {
        this.log(new Log(type, timestamp, description));
    }

}
