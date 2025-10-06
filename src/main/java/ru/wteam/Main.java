package ru.wteam;

//import org.apache.logging.log4j.core.config.ConfigurationFactory;
import org.hibernate.SessionFactory;
import ru.wteam.DAO.UserDAOClass;
import ru.wteam.Service.UserService;
import ru.wteam.entity.User;

import java.util.List;
import java.util.Scanner;


import org.hibernate.cfg.Configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
    private static final UserDAOClass userDAO = new UserDAOClass(sessionFactory);
    private static final UserService userService = new UserService(userDAO);

    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {

        while (true) {
            logger.info("Старт цикла. Вывод основного меню для выбора действия");
            System.out.println("Enter comand nomber:    1.Create    2.Read all    3.Update    4.Delete    5.Exit");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    logger.info("Пользователь ввел: 1");
                    createUser();
                    break;
                case 2:
                    logger.info("Пользователь ввел: 2");
                    readUsers();
                    break;
                case 3:
                    logger.info("Пользователь ввел: 3");
                    updateUser();
                    break;
                case 4:
                    logger.info("Пользователь ввел: 4");
                    deleteUser();
                    break;
                case 5:
                    logger.info("Пользователь ввел: 5");
                    return;
                default:
                    logger.info("Пользователь ввел что-то не то");
                    System.out.println("Wrong choice, try again!");
            }
        }
    }

    private static void createUser() {
        logger.info("Создание записи в БД таблица Users");
        User user = new User();
        System.out.println("Create user");
        System.out.print("Name: ");
        user.setName(scanner.next());
        System.out.print("Email: ");
        user.setEmail(scanner.next());
        System.out.print("Age: ");
        user.setAge(scanner.nextInt());

        userService.createUser(user);
//        System.out.println("User has been saved!");
        logger.info("Запись успешно создана");
    }

    private static void readUsers() {
        logger.info("Чтение записи из БД таблица Users");
        List<User> users = userService.readAllUsers();
        for (User user : users) {System.out.println(user);
        }
        logger.info("Чтение успешно завершено");
    }

    private static void updateUser() {
        logger.info("Обновление данных в БД таблица Users");
        System.out.print("Enter user ID: ");
        Long id = scanner.nextLong();
        User user = userService.readUser(id);
        if (user != null) {
            System.out.print("New name: ");
            user.setName(scanner.next());
            System.out.print("New email: ");
            user.setEmail(scanner.next());
            System.out.print("New age: ");
            user.setAge(scanner.nextInt());
            userService.updateUser(user);
            logger.info("Обновление успешно завершено");
        } else {
            logger.info("Запись для обновления не найдена");
        }
    }

    private static void deleteUser() {
        logger.info("Удаление записи из БД таблица Users");
        System.out.print("Enter User ID: ");
        Long id = scanner.nextLong();
        userService.deleteUser(id);
        logger.info("Запиь успешно удалена");
    }
}
