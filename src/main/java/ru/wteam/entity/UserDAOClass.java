package ru.wteam.entity;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import java.util.List;

public class UserDAOClass implements UserDAO {
    private Session session;

    public UserDAOClass(Session session) {
        this.session = session;
    }

    @Override
    public void createUser(User user) {
        Transaction transaction = session.beginTransaction();
        session.persist(user);
        session.getTransaction().commit();
    }

    @Override
    public User readUser(Long id) {
        return session.find(User.class, id);
    }

    @Override
    public void updateUser(User user) {
        Transaction transaction = session.beginTransaction();
        session.merge(user);
        session.getTransaction().commit();
    }

    @Override
    public void deleteUser(Long id) {
        Transaction transaction = session.beginTransaction();
        User user = session.find(User.class, id);
        if (user != null) {
            session.remove(user);
            session.getTransaction().commit();
        }
    }

    @Override
    public List<User> readAllUsers() {
        Query<User> query = session.createQuery("FROM User", User.class);
        return query.list();
    }
}
