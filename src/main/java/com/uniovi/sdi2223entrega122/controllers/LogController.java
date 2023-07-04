package com.uniovi.sdi2223entrega122.controllers;


import com.uniovi.sdi2223entrega122.entities.Log;
import com.uniovi.sdi2223entrega122.services.LoggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class LogController {

    @Autowired
    private LoggerService loggerService;

    @RequestMapping("/log/list")
    public String getList(Model model, @RequestParam(required = false) String type) {

        List<Log> logs;

        if (type != null && !type.isEmpty()) {
            logs = loggerService.getAllLogsByType(type);
        } else {
            logs = loggerService.getAllLogs();
        }

        model.addAttribute("logList", logs);
        model.addAttribute("type", type);

        return "log/list";
    }

    @RequestMapping("/log/delete")
    public String deleteLog() {
        loggerService.deleteAllLogs();
        return "redirect:/log/list";
    }
}
