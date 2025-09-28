package ru.wteam;

import ru.wteam.entity.UserService;
import ru.wteam.entity.User;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final UserService userService = new UserService();

    public static void main(String[] args) {
        System.out.println("Hi!");

        while (true) {
            System.out.println("Enter comand nomber:    1.Create    2.Read all    3.Update    4.Delete    5.Exit");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    createUser();
                    break;
                case 2:
                    readUsers();
                    break;
                case 3:
                    updateUser();
                    break;
                case 4:
                    deleteUser();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Wrong choice, try again!");
            }
        }
    }

    private static void createUser() {
        User user = new User();
        System.out.println("Create user");
        System.out.print("Name: ");
        user.setName(scanner.next());
        System.out.print("Email: ");
        user.setEmail(scanner.next());
        System.out.print("Age: ");
        user.setAge(scanner.nextInt());

        userService.createUser(user);
        System.out.println("User has been saved!");
    }

    private static void readUsers() {
        List<User> users = userService.readAllUsers();
        for (User user : users) {System.out.println(user);
        }
    }

    private static void updateUser() {
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
            System.out.println("User has been updated!");
        } else {
            System.out.println("User has not been found!");
        }
    }

    private static void deleteUser() {
        System.out.print("Enter User ID: ");
        Long id = scanner.nextLong();
        userService.deleteUser(id);
        System.out.println("User has been deleted!");
    }
}
