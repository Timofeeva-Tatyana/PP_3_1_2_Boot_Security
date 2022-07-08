package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserDao {
    List<User> getAllUsers();
    void saveUser(User user);
    User findUserById(Long id);
    void updateUser(Long id, User user);
    void deleteUser(Long id);
    User findByUserName(String name);
}
