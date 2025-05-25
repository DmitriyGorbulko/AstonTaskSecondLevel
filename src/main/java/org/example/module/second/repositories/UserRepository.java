package org.example.module.second.repositories;


import org.example.module.second.entities.User;

import java.util.List;

public interface UserRepository {
    void save(User user);
    User findById(Long id);
    List<User> findAll();
    void update(User user);
    void delete(User user);
}
