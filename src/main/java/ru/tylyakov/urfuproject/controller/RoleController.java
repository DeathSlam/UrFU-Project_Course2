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

    @PostMapping("/add-user")
    public String addUserRole(@RequestParam String username) {
        userService.addRoleToUser(username, "USER");
        return "redirect:/roles";
    }

    @PostMapping("/add-admin")
    public String addAdminRole(@RequestParam String username) {
        userService.addRoleToUser(username, "ADMIN");
        return "redirect:/roles";
    }
}
