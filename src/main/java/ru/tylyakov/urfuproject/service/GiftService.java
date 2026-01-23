package ru.tylyakov.urfuproject.service;

import org.springframework.security.core.Authentication;
import ru.tylyakov.urfuproject.entity.Gift;

import java.util.List;

public interface GiftService {

    List<Gift> findByBirthday(Long birthdayId);

    void save(Gift gift, Authentication authentication);

    void delete(Long id, Authentication authentication);
}