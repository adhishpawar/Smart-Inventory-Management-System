package org.inventory;

import org.inventory.models.User;
import org.inventory.models.Product;
import org.inventory.services.AuthService;
import org.inventory.services.InventoryService;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    private static final AuthService authService = new AuthService();
    private static final InventoryService inventoryService = new InventoryService();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        User loggedInUser = null;

        while (true) {
            System.out.println("\n1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    registerUser(scanner);
                    break;
                case 2:
                    loggedInUser = loginUser(scanner);
                    if (loggedInUser.getRole().equals("Admin")) {
                        manageInventory(scanner, loggedInUser);
                    } else if (loggedInUser.getRole().equals("User")) {
                        manageOrders(scanner,loggedInUser);
                    }
                    break;
                case 3:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void manageOrders(Scanner scanner, User loggedInUser) {
    }

    private static void registerUser(Scanner scanner) {
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
    }

    private static User loginUser(Scanner scanner) {
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine();

        User user = authService.login(email, password);
        if (user != null) {
            System.out.println("Login successful! Welcome, " + user.getName() + " (" + user.getRole() + ")");
        } else {
            System.out.println("Invalid credentials.");
        }
        return user;
    }

    private static void manageInventory(Scanner scanner, User user) {
        while (true) {
            System.out.println("\n===== Inventory & Order Management =====");
            System.out.println("1. Add Product");
            System.out.println("2. Update Product");
            System.out.println("3. Delete Product");
            System.out.println("4. Search Product");
            System.out.println("5. Display Inventory");
            System.out.println("6. Place Order");
            System.out.println("7. View Orders");
            System.out.println("8. Logout");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addProduct(scanner);
                    break;
                case 2:
                    updateProduct(scanner);
                    break;
                case 3:
                    deleteProduct(scanner);
                    break;
                case 4:
                    searchProduct(scanner);
                    break;
                case 5:
                    inventoryService.displayAllInventory();
                    break;
                case 6:
                    placeOrder(scanner, user);
                    break;

                case 8:
                    System.out.println("Logging out...");
                    return;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void addProduct(Scanner scanner) {
        System.out.print("Enter Product Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Category: ");
        String category = scanner.nextLine();
        System.out.print("Enter Price: ");
        double price = scanner.nextDouble();
        System.out.print("Enter Stock: ");
        int stock = scanner.nextInt();
        inventoryService.addProduct(name, category, price, stock);
    }

    private static void updateProduct(Scanner scanner) {
        System.out.print("Enter Name: ");
        String Name = scanner.nextLine();
        System.out.print("Enter New Category: ");
        String newCategory = scanner.nextLine();
        System.out.print("Enter New Price: ");
        double newPrice = scanner.nextDouble();
        System.out.print("Enter New Stock: ");
        int newStock = scanner.nextInt();
        inventoryService.updateProduct(Name, newCategory, newPrice, newStock);

    }

    private static void deleteProduct(Scanner scanner) {
        System.out.print("Enter Product name to Delete: ");
        String deleteName = scanner.nextLine();
        Product product = inventoryService.searchProductByUsingName(deleteName);
        InventoryService.displayProduct(product);
        System.out.println("Are you Sure? (y/n)");
        String choice = scanner.nextLine();
        if(Objects.equals(choice, "y"))
            inventoryService.deleteProduct(product);

    }

    private static void searchProduct(Scanner scanner) {
        System.out.println("Enter the By which Searching \n 1.ByName \n 2.ByCategory");
        int choice = scanner.nextInt();
        if(choice == 1) {
            System.out.print("Enter Product Name to Search: ");
            String searchName = scanner.nextLine();
            Product product = inventoryService.searchProductByUsingName(searchName);
            InventoryService.displayProduct(product);

        }else if(choice == 2){
            System.out.print("Enter Product Category to Search: ");
            String searchCategory = scanner.nextLine().trim();
            List<Product> products = inventoryService.searchProductByUsingCategory(searchCategory);
            InventoryService.displayAllInventory(products);

        }

    }

    private static void placeOrder(Scanner scanner, User user) {
        System.out.print("Enter Product ID: ");
        int productId = scanner.nextInt();
        System.out.print("Enter Quantity: ");
        int quantity = scanner.nextInt();
//        orderService.placeOrder(user, productId, quantity);
        System.out.print("Working in Progress");
    }

}
