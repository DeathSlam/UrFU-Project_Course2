package ru.tylyakov.urfuproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.tylyakov.urfuproject.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}
