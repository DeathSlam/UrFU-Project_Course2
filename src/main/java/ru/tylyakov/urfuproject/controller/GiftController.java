package ru.tylyakov.urfuproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.tylyakov.urfuproject.entity.Gift;
import ru.tylyakov.urfuproject.service.GiftService;

@Controller
@RequestMapping("/gifts")
@RequiredArgsConstructor
public class GiftController {

    private final GiftService giftService;

    @GetMapping("/{birthdayId}")
    public String list(@PathVariable Long birthdayId, Model model) {
        model.addAttribute("gifts", giftService.findByBirthday(birthdayId));
        model.addAttribute("birthdayId", birthdayId);
        return "gifts";
    }

    @GetMapping("/new/{birthdayId}")
    public String createForm(@PathVariable Long birthdayId, Model model) {
        Gift gift = new Gift();
        model.addAttribute("gift", gift);
        model.addAttribute("birthdayId", birthdayId);
        return "gift-form";
    }

    @PostMapping
    public String save(@ModelAttribute Gift gift) {
        giftService.save(gift);
        return "redirect:/birthdays";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        giftService.delete(id);
        return "redirect:/birthdays";
    }
}
