package ru.tylyakov.urfuproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ActuatorController {

    @GetMapping("/monitoring")
    public String monitoringPage() {
        return "actuator";
    }
}