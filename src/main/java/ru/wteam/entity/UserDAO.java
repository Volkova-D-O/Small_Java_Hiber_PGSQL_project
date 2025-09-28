package ru.wteam.entity;
import java.util.List;

public interface UserDAO {
    void createUser(User user);
    User readUser(Long id);
    void updateUser(User user);
    void deleteUser(Long id);

    List<User> readAllUsers();
}

