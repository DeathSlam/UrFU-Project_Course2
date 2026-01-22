package ru.tylyakov.urfuproject.service;

public interface CalculationService {

    Double calculateTotalGiftsPrice(Long birthdayId);

    Double calculateAverageGiftPrice(Long birthdayId);
}
