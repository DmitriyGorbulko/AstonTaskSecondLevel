package org.example.module.second.services;

import org.apache.log4j.Logger;
import org.example.module.second.dao.UserDAO;
import org.example.module.second.entities.User;


public class UserServiceImpl implements UserService {
    private final UserDAO dao;

    public UserServiceImpl(UserDAO dao) {
        this.dao = dao;
    }
    private static final Logger logger = Logger.getLogger(UserServiceImpl.class);



    @Override
    public void createUser(String name, String email, int age) {
        if (dao.findByEmail(email) != null) {
            logger.warn("User with email already exists: " + email + " [createUser]");
            System.out.println("Error: Email already exists.");
            return;
        }

        User user = new User(name, email, age);
        dao.save(user);
    }

    @Override
    public User getUser(Long id) {
        return dao.findById(id);
    }


    @Override
    public void updateUser(Long id, String email) {
        if (dao.findByEmail(email) != null) {
            logger.warn("Attempt to use existing email: " + email + " [updateUser]");
            System.out.println("Error: Email already exists.");
            return;
        }

        dao.updateEmail(id, email);
    }

    @Override
    public void deleteUser(Long id) {
        User user = dao.findById(id);
        if (user != null) {
            dao.delete(user);
        } else {
            System.out.println("User not found");
        }
    }
}
