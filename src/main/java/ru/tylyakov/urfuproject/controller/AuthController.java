package ru.tylyakov.urfuproject.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.tylyakov.urfuproject.entity.User;
import ru.tylyakov.urfuproject.service.UserService;


@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String register(
            @Valid @ModelAttribute User user,
            BindingResult result) {

        if (result.hasErrors()) {
            return "register";
        }

        userService.register(user);
        return "redirect:/login";
    }
}