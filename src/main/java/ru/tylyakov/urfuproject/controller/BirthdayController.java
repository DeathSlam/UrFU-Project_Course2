package ru.tylyakov.urfuproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import ru.tylyakov.urfuproject.entity.Birthday;
import ru.tylyakov.urfuproject.service.BirthdayService;

@Controller
@RequestMapping("/birthdays")
@RequiredArgsConstructor
public class BirthdayController {

    private final BirthdayService birthdayService;

    @GetMapping
    public String list(Model model, Authentication authentication) {
        model.addAttribute("birthdays", birthdayService.findAll(authentication));
        return "birthdays";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("birthday", new Birthday());
        return "birthday-form";
    }

    @PostMapping
    public String save(
            @Valid @ModelAttribute Birthday birthday,
            BindingResult result,
            Authentication authentication) {

        if (result.hasErrors()) {
            return "birthday-form";
        }

        birthdayService.save(birthday, authentication);
        return "redirect:/birthdays";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, Authentication authentication) {
        birthdayService.delete(id, authentication);
        return "redirect:/birthdays";
    }
}
