package ru.tylyakov.urfuproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tylyakov.urfuproject.entity.Gift;

import java.util.List;

public interface GiftRepository extends JpaRepository<Gift, Long> {

    List<Gift> findByBirthdayId(Long birthdayId);
}
