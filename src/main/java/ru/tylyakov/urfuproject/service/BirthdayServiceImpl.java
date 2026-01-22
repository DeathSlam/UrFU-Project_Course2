package ru.tylyakov.urfuproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import ru.tylyakov.urfuproject.entity.Birthday;
import ru.tylyakov.urfuproject.repository.BirthdayRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BirthdayServiceImpl implements BirthdayService {

    private final BirthdayRepository birthdayRepository;

    @Override
    public List<Birthday> findAll(Authentication authentication) {

        boolean isAdmin = authentication.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (isAdmin) {
            return birthdayRepository.findAll();
        }

        return birthdayRepository.findByCreatedBy(authentication.getName());
    }

    @Override
    public Birthday findById(Long id) {
        return birthdayRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Запись не найдена"));
    }

    @Override
    public void save(Birthday birthday, Authentication authentication) {
        birthday.setCreatedBy(authentication.getName());
        birthdayRepository.save(birthday);
    }

    @Override
    public void delete(Long id, Authentication authentication) {

        Birthday birthday = findById(id);

        boolean isAdmin = authentication.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        if (!isAdmin && !birthday.getCreatedBy().equals(authentication.getName())) {
            throw new RuntimeException("Нет прав на удаление");
        }

        birthdayRepository.deleteById(id);
    }
}
