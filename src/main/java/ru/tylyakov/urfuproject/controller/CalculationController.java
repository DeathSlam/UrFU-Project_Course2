package ru.tylyakov.urfuproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.tylyakov.urfuproject.service.CalculationService;

@Controller
@RequestMapping("/calculation")
@RequiredArgsConstructor
public class CalculationController {

    private final CalculationService calculationService;

    @GetMapping("/{birthdayId}")
    public String calculationPage(@PathVariable Long birthdayId, Model model) {

        Double total = calculationService.calculateTotalGiftsPrice(birthdayId);
        Double average = calculationService.calculateAverageGiftPrice(birthdayId);

        model.addAttribute("birthdayId", birthdayId);
        model.addAttribute("totalPrice", total);
        model.addAttribute("averagePrice", average);

        return "calculation";
    }
}
