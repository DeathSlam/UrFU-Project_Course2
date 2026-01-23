package ru.tylyakov.urfuproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.tylyakov.urfuproject.service.UserService;

@Controller
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {

    private final UserService userService;

    @GetMapping
    public String rolesPage(Model model) {
        model.addAttribute("users", userService.findAllUsers());
        return "roles";
    }

    @PostMapping("/set-guest")
    public String setGuestRole(@RequestParam String username) {
        userService.setUserRole(username, "READ_ONLY");
        return "redirect:/roles";
    }

    @PostMapping("/set-user")
    public String setUserRole(@RequestParam String username) {
        userService.setUserRole(username, "USER");
        return "redirect:/roles";
    }

    @PostMapping("/set-admin")
    public String setAdminRole(@RequestParam String username) {
        userService.setUserRole(username, "ADMIN");
        return "redirect:/roles";
    }
}