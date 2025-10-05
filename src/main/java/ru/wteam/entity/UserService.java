package ru.wteam.entity;

import java.util.List;

public class UserService {

    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void createUser(User user) {
        userDAO.createUser(user);
    }

    public User readUser(Long id) {
        return userDAO.readUser(id);
    }

    public void updateUser(User user) {
        userDAO.updateUser(user);
    }

    public void deleteUser(Long id) {
        userDAO.deleteUser(id);
    }

    public List<User> readAllUsers() {
        return userDAO.readAllUsers();
    }
}
