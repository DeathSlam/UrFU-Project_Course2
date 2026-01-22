package ru.tylyakov.urfuproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tylyakov.urfuproject.entity.Gift;
import ru.tylyakov.urfuproject.repository.GiftRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CalculationServiceImpl implements CalculationService {

    private final GiftRepository giftRepository;

    @Override
    public Double calculateTotalGiftsPrice(Long birthdayId) {

        List<Gift> gifts = giftRepository.findByBirthdayId(birthdayId);

        return gifts.stream()
                .mapToDouble(Gift::getPrice)
                .sum();
    }

    @Override
    public Double calculateAverageGiftPrice(Long birthdayId) {

        List<Gift> gifts = giftRepository.findByBirthdayId(birthdayId);

        if (gifts.isEmpty()) {
            return 0.0;
        }

        return gifts.stream()
                .mapToDouble(Gift::getPrice)
                .average()
                .orElse(0.0);
    }
}
