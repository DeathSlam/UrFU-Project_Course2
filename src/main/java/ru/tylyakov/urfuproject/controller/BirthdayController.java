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
    public String list(Model model, Authentication authentication, @RequestParam(required = false) String error) {
        model.addAttribute("birthdays", birthdayService.findAll(authentication));

        if ("no_permission".equals(error)) {
            model.addAttribute("error", "У вас нет прав для выполнения этого действия");
        }

        return "birthdays";
    }

    @GetMapping("/new")
    public String createForm(Model model, Authentication authentication) {

        // Проверка прав доступа
        boolean isReadOnly = authentication.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_READ_ONLY"));

        if (isReadOnly) {
            return "redirect:/birthdays?error=no_permission";
        }

        model.addAttribute("birthday", new Birthday());
        return "birthday-form";
    }

    @PostMapping
    public String save(
            @Valid @ModelAttribute Birthday birthday,
            BindingResult result,
            Authentication authentication,
            Model model) {

        if (result.hasErrors()) {
            return "birthday-form";
        }

        try {
            birthdayService.save(birthday, authentication);
            return "redirect:/birthdays";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "birthday-form";
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, Authentication authentication) {
        try {
            birthdayService.delete(id, authentication);
        } catch (RuntimeException e) {
            return "redirect:/birthdays?error=no_permission";
        }
        return "redirect:/birthdays";
    }
}