package ru.tylyakov.urfuproject.service;

import org.springframework.security.core.Authentication;
import ru.tylyakov.urfuproject.entity.Birthday;

import java.util.List;

public interface BirthdayService {

    List<Birthday> findAll(Authentication authentication);

    Birthday findById(Long id);

    void save(Birthday birthday, Authentication authentication);

    void delete(Long id, Authentication authentication);
}
