package org.example.module.second.dao;

import org.example.module.second.entities.User;

public interface UserDAO {
    void save(User user);
    User findById(Long id);
    User findByEmail(String email);
    void updateEmail(Long id, String newEmail);
    void delete(User user);
}
