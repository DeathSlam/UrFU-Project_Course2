package ru.tylyakov.urfuproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
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
    public void save(Gift gift, Authentication authentication) {

        // Проверка прав доступа
        boolean isReadOnly = authentication.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_READ_ONLY"));

        if (isReadOnly) {
            throw new RuntimeException("У вас нет прав для создания подарков. Обратитесь к администратору для повышения роли.");
        }

        giftRepository.save(gift);
    }

    @Override
    public void delete(Long id, Authentication authentication) {

        // Проверка прав доступа
        boolean isReadOnly = authentication.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_READ_ONLY"));

        if (isReadOnly) {
            throw new RuntimeException("У вас нет прав для удаления подарков. Обратитесь к администратору для повышения роли.");
        }

        giftRepository.deleteById(id);
    }
}