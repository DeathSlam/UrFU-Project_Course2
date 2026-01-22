package ru.tylyakov.urfuproject.service;

import ru.tylyakov.urfuproject.entity.Gift;

import java.util.List;

public interface GiftService {

    List<Gift> findByBirthday(Long birthdayId);

    void save(Gift gift);

    void delete(Long id);
}