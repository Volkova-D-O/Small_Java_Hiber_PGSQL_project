package ru.wteam.entity;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class UserService {
    private SessionFactory sessionFactory;

    public UserService() {
        sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
    }

    public void createUser(User user) {
        try (Session session = sessionFactory.openSession()) {
            UserDAOClass userDAO = new UserDAOClass(session);
            userDAO.createUser(user);
        }
    }

    public User readUser(Long id) {
        try (Session session = sessionFactory.openSession()) {
            UserDAOClass userDAO = new UserDAOClass(session);
            return userDAO.readUser(id);
        }
    }

    public void updateUser(User user) {
        try (Session session = sessionFactory.openSession()) {
            UserDAOClass userDAO = new UserDAOClass(session);
            userDAO.updateUser(user);
        }
    }

    public void deleteUser(Long id) {
        try (Session session = sessionFactory.openSession()) {
            UserDAOClass userDAO = new UserDAOClass(session);
            userDAO.deleteUser(id);
        }
    }

    public List<User> readAllUsers() {
        try (Session session = sessionFactory.openSession()) {
            UserDAOClass userDAO = new UserDAOClass(session);
            return userDAO.readAllUsers();
        }
    }
}
