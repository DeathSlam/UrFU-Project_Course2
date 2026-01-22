package ru.tylyakov.urfuproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.tylyakov.urfuproject.entity.Gift;
import ru.tylyakov.urfuproject.repository.GiftRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GiftServiceImpl implements GiftService {

    private final GiftRepository giftRepository;

    @Override
    public List<Gift> findByBirthday(Long birthdayId) {
        return giftRepository.findByBirthdayId(birthdayId);
    }

    @Override
    public void save(Gift gift) {
        giftRepository.save(gift);
    }

    @Override
    public void delete(Long id) {
        giftRepository.deleteById(id);
    }
}
