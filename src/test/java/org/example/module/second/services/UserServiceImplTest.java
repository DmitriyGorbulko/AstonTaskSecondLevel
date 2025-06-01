package org.example.module.second.services;

import org.example.module.second.dao.UserDAO;
import org.example.module.second.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    private UserDAO userDAO;
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        userDAO = mock(UserDAO.class);
        userService = new UserServiceImpl(userDAO);
    }

    @Test
    void testCreateUser_success() {
        when(userDAO.findByEmail("test@example.com")).thenReturn(null);

        userService.createUser("John", "test@example.com", 30);

        verify(userDAO, times(1)).save(any(User.class));
    }

    @Test
    void testCreateUser_emailExists() {
        when(userDAO.findByEmail("test@example.com")).thenReturn(new User());

        userService.createUser("John", "test@example.com", 30);

        verify(userDAO, never()).save(any(User.class));
    }

    @Test
    void testUpdateUser_emailNotExists() {
        when(userDAO.findByEmail("new@example.com")).thenReturn(null);

        userService.updateUser(1L, "new@example.com");

        verify(userDAO, times(1)).updateEmail(1L, "new@example.com");
    }

    @Test
    void testUpdateUser_emailAlreadyExists() {
        when(userDAO.findByEmail("existing@example.com")).thenReturn(new User());

        userService.updateUser(1L, "existing@example.com");

        verify(userDAO, never()).updateEmail(any(), any());
    }

    @Test
    void testDeleteUser_userExists() {
        User user = new User("John", "john@example.com", 25);
        user.setId(1L);
        when(userDAO.findById(1L)).thenReturn(user);

        userService.deleteUser(1L);

        verify(userDAO, times(1)).delete(user);
    }

    @Test
    void testDeleteUser_userNotFound() {
        when(userDAO.findById(1L)).thenReturn(null);

        userService.deleteUser(1L);

        verify(userDAO, never()).delete(any());
    }
}
