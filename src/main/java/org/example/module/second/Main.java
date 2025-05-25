package org.example.module.second;

import org.apache.log4j.Logger;
import org.example.module.second.dao.UserDAO;
import org.example.module.second.dao.UserDAOImpl;
import org.example.module.second.entities.User;

import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final UserDAO dao = new UserDAOImpl();
    private static final Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n--- User Service ---");
            System.out.println("1. Create user");
            System.out.println("2. Get user by email");
            System.out.println("3. Update email by ID");
            System.out.println("4. Delete user by ID");
            System.out.println("5. Exit");
            System.out.print("Choose option: ");


            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> createUser();
                case 2 -> getUserByEmail();
                case 3 -> updateEmailById();
                case 4 -> deleteUserById();
                case 5 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice");
            }
        }
    }

    private static void createUser() {
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Age: ");
        int age = Integer.parseInt(scanner.nextLine());

        User user = new User(name, email, age);
        dao.save(user);
    }

    private static void getUserByEmail() {
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        User user = dao.findByEmail(email);
        if (user != null) {
            System.out.println(user);
        } else {
            System.out.println("User not found");
        }
    }

    private static void updateEmailById() {
        System.out.print("User ID: ");
        Long id = Long.parseLong(scanner.nextLine());
        System.out.print("New email: ");
        String email = scanner.nextLine();
        dao.updateEmail(id, email);
    }

    private static void deleteUserById() {
        System.out.print("User ID: ");
        Long id = Long.parseLong(scanner.nextLine());
        User user = dao.findById(id);
        if (user != null) {
            dao.delete(user);
            System.out.println("User deleted");
        } else {
            System.out.println("User not found");
        }
    }
}
