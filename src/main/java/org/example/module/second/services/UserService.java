package org.example.module.second.services;

import org.example.module.second.entities.User;

public interface UserService {
    void createUser(String name, String email, int age);
    User getUser(Long id);
    void updateUser(Long id, String email);
    void deleteUser(Long id);
}
