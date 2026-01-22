package ru.tylyakov.urfuproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tylyakov.urfuproject.entity.Birthday;

import java.util.List;

public interface BirthdayRepository extends JpaRepository<Birthday, Long> {

    List<Birthday> findByCreatedBy(String username);
}
