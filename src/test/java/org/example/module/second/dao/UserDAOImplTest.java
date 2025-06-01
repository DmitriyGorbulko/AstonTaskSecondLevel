package org.example.module.second.dao;

import org.example.module.second.entities.User;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserDAOImplTest {

    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    private UserDAO userDAO;

    @BeforeAll
    void startContainer() {
        postgres.start();
    }

    @BeforeEach
    void setUp() {
        Configuration config = new Configuration();
        Properties props = new Properties();
        props.put("hibernate.connection.url", postgres.getJdbcUrl());
        props.put("hibernate.connection.username", postgres.getUsername());
        props.put("hibernate.connection.password", postgres.getPassword());
        props.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        props.put("hibernate.hbm2ddl.auto", "create-drop");
        props.put("hibernate.show_sql", "true");

        config.addProperties(props);
        config.addAnnotatedClass(User.class);

        userDAO = new UserDAOImpl(config.buildSessionFactory());
    }

    @Test
    void testSaveAndFindById() {
        User user = new User("Test", "test@example.com", 20);
        userDAO.save(user);

        User found = userDAO.findById(user.getId());
        assertNotNull(found);
        assertEquals("test@example.com", found.getEmail());
    }

    @Test
    void testUpdateEmail() {
        User user = new User("Update", "old@example.com", 25);
        userDAO.save(user);

        userDAO.updateEmail(user.getId(), "new@example.com");

        User updated = userDAO.findById(user.getId());
        assertEquals("new@example.com", updated.getEmail());
    }

    @Test
    void testDelete() {
        User user = new User("Delete", "delete@example.com", 33);
        userDAO.save(user);
        userDAO.delete(user);

        assertNull(userDAO.findById(user.getId()));
    }
}
