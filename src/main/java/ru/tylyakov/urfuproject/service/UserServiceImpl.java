package ru.tylyakov.urfuproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.tylyakov.urfuproject.entity.Role;
import ru.tylyakov.urfuproject.entity.User;
import ru.tylyakov.urfuproject.repository.RoleRepository;
import ru.tylyakov.urfuproject.repository.UserRepository;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void register(User user) {

        // кодирование пароля
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // роль по умолчанию
        Role readOnlyRole = roleRepository.findByName("ROLE_READ_ONLY")
                .orElseThrow(() -> new RuntimeException("Роль READ_ONLY не найдена"));

        user.setRoles(Set.of(readOnlyRole));

        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
    }

    @Override
    public void addRoleToUser(String username, String roleName) {

        User user = findByUsername(username);

        Role role = roleRepository.findByName("ROLE_" + roleName)
                .orElseThrow(() -> new RuntimeException("Роль не найдена"));

        user.getRoles().add(role);
        userRepository.save(user);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
}
