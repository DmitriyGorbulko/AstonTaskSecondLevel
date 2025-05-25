package org.example.module.second.dao;

import org.apache.log4j.Logger;
import org.example.module.second.entities.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class UserDAOImpl implements UserDAO {

    private static final Logger logger = Logger.getLogger(UserDAOImpl.class);
    private final SessionFactory sessionFactory;

    public UserDAOImpl() {
        Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
        sessionFactory = configuration.buildSessionFactory();
    }

    @Override
    public void save(User user) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            logger.info("Code: 200 | Method: save | User saved with ID = " + user.getId());
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error("Code: 500 | Method: save | Error saving user - " + e.getMessage(), e);
            System.out.println("Error: Email already exists or other issue.");
        }
    }

    @Override
    public User findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            User user = session.get(User.class, id);
            logger.info("Code: 200 | Method: findById | User fetched with ID = " + id);
            return user;
        } catch (Exception e) {
            logger.error("Code: 500 | Method: findById | Error - " + e.getMessage(), e);
            return null;
        }
    }

    @Override
    public User findByEmail(String email) {
        try (Session session = sessionFactory.openSession()) {
            User user = session
                    .createQuery("FROM User WHERE email = :email", User.class)
                    .setParameter("email", email)
                    .uniqueResult();
            logger.info("Code: 200 | Method: findByEmail | Lookup for email = " + email);
            return user;
        } catch (Exception e) {
            logger.error("Code: 500 | Method: findByEmail | Error - " + e.getMessage(), e);
            return null;
        }
    }

    @Override
    public void updateEmail(Long id, String newEmail) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            User user = session.get(User.class, id);
            if (user == null) {
                logger.warn("Code: 404 | Method: updateEmail | User not found with ID = " + id);
                System.out.println("User not found.");
                return;
            }

            User existing = findByEmail(newEmail);
            if (existing != null && !existing.getId().equals(id)) {
                logger.warn("Code: 409 | Method: updateEmail | Email already in use: " + newEmail);
                System.out.println("Email already in use.");
                return;
            }

            user.setEmail(newEmail);
            session.update(user);
            transaction.commit();

            logger.info("Code: 200 | Method: updateEmail | Email updated for ID = " + id);
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error("Code: 500 | Method: updateEmail | Error - " + e.getMessage(), e);
        }
    }

    @Override
    public void delete(User user) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();
            session.delete(user);
            transaction.commit();
            logger.info("Code: 200 | Method: delete | User deleted with ID = " + user.getId());
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            logger.error("Code: 500 | Method: delete | Error - " + e.getMessage(), e);
        }
    }
}
