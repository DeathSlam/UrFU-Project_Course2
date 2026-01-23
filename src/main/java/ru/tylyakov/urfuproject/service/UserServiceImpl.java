package ru.tylyakov.urfuproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.tylyakov.urfuproject.entity.Role;
import ru.tylyakov.urfuproject.entity.User;
import ru.tylyakov.urfuproject.repository.RoleRepository;
import ru.tylyakov.urfuproject.repository.UserRepository;

import java.util.HashSet;
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

        // Используем HashSet для изменяемого множества
        Set<Role> roles = new HashSet<>();
        roles.add(readOnlyRole);
        user.setRoles(roles);

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

        // Если множество неизменяемое, создаём новое
        if (user.getRoles() == null) {
            user.setRoles(new HashSet<>());
        }

        // Проверяем, можно ли изменить существующее множество
        try {
            user.getRoles().add(role);
        } catch (UnsupportedOperationException e) {
            // Если множество неизменяемое, создаём новое
            Set<Role> newRoles = new HashSet<>(user.getRoles());
            newRoles.add(role);
            user.setRoles(newRoles);
        }

        userRepository.save(user);
    }

    @Override
    public void setUserRole(String username, String roleName) {
        User user = findByUsername(username);

        Role role = roleRepository.findByName("ROLE_" + roleName)
                .orElseThrow(() -> new RuntimeException("Роль не найдена"));

        // Создаём новое изменяемое множество с одной ролью
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        user.setRoles(roles);

        userRepository.save(user);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
}