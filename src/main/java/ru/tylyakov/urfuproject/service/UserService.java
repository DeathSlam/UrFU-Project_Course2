package ru.tylyakov.urfuproject.service;

import ru.tylyakov.urfuproject.entity.User;

import java.util.List;

public interface UserService {

    void register(User user);

    User findByUsername(String username);

    void addRoleToUser(String username, String roleName);

    void setUserRole(String username, String roleName);

    List<User> findAllUsers();
}