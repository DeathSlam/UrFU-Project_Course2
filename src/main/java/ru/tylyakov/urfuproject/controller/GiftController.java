package ru.tylyakov.urfuproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import ru.tylyakov.urfuproject.entity.Birthday;
import ru.tylyakov.urfuproject.entity.Gift;
import ru.tylyakov.urfuproject.service.BirthdayService;
import ru.tylyakov.urfuproject.service.GiftService;

@Controller
@RequestMapping("/gifts")
@RequiredArgsConstructor
public class GiftController {

    private final GiftService giftService;
    private final BirthdayService birthdayService;

    @GetMapping("/{birthdayId}")
    public String list(@PathVariable Long birthdayId, Model model) {
        model.addAttribute("gifts", giftService.findByBirthday(birthdayId));
        model.addAttribute("birthdayId", birthdayId);
        return "gifts";
    }

    @GetMapping("/new/{birthdayId}")
    public String createForm(@PathVariable Long birthdayId,
                             Model model,
                             Authentication authentication) {

        boolean isReadOnly = authentication.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_READ_ONLY"));

        if (isReadOnly) {
            return "redirect:/gifts/" + birthdayId + "?error=no_permission";
        }

        Gift gift = new Gift();

        Birthday birthday = birthdayService.findById(birthdayId);
        gift.setBirthday(birthday);

        model.addAttribute("gift", gift);
        return "gift-form";
    }


    @PostMapping
    public String save(
            @Valid @ModelAttribute Gift gift,
            BindingResult result,
            @RequestParam Long birthdayId,
            Model model,
            Authentication authentication) {

        if (result.hasErrors()) {
            model.addAttribute("birthdayId", birthdayId);
            return "gift-form";
        }

        try {
            // Связываем подарок с днём рождения
            Birthday birthday = birthdayService.findById(birthdayId);
            gift.setBirthday(birthday);

            giftService.save(gift, authentication);
            return "redirect:/gifts/" + birthdayId;
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("birthdayId", birthdayId);
            return "gift-form";
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, @RequestParam Long birthdayId, Authentication authentication) {
        try {
            giftService.delete(id, authentication);
        } catch (RuntimeException e) {
            return "redirect:/gifts/" + birthdayId + "?error=no_permission";
        }
        return "redirect:/gifts/" + birthdayId;
    }
}