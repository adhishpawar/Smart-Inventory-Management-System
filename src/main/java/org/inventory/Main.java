package org.inventory;

import org.inventory.models.User;
import org.inventory.services.AuthService;

import java.util.*;

public class Main {
    private static final AuthService authService = new AuthService();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Email: ");
                    String email = scanner.nextLine();
                    System.out.print("Enter Password: ");
                    String password = scanner.nextLine();
                    System.out.print("Enter Role (ADMIN/EMPLOYEE): ");
                    String role = scanner.nextLine().toUpperCase();

                    if (authService.register(name, email, password, role)) {
                        System.out.println("User registered successfully!");
                    } else {
                        System.out.println("Registration failed.");
                    }
                    break;
                case 2:
                    System.out.print("Enter Email: ");
                    email = scanner.nextLine();
                    System.out.print("Enter Password: ");
                    password = scanner.nextLine();

                    User user = authService.login(email, password);
                    if (user != null) {
                        System.out.println("Login successful! Welcome, " + user.getName() + " (" + user.getRole() + ")");
                    } else {
                        System.out.println("Invalid credentials.");
                    }
                    break;
                case 3:
                    System.out.println("Exiting...");
                    return;
            }
        }
    }
}