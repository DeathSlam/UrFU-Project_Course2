package ru.tylyakov.urfuproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
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
    public String rolesPage(Model model, Authentication authentication, @RequestParam(required = false) String error) {
        model.addAttribute("users", userService.findAllUsers());
        model.addAttribute("currentUsername", authentication.getName());

        if ("cannot_delete_self".equals(error)) {
            model.addAttribute("error", "Вы не можете удалить свою учётную запись");
        }

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

    @PostMapping("/delete")
    public String deleteUser(@RequestParam String username, Authentication authentication) {
        try {
            userService.deleteUser(username, authentication.getName());
            return "redirect:/roles";
        } catch (RuntimeException e) {
            return "redirect:/roles?error=cannot_delete_self";
        }
    }
}