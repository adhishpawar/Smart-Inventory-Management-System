package org.inventory.services;

import org.inventory.dao.InventoryDAO;
import org.inventory.models.Product;
import org.inventory.models.User;
import org.inventory.utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderService {
    public static void placeOrder(User user, Product product, int quantity) {
        int remainingQunt = product.getQuantity() - quantity;
        product.setQuantity(remainingQunt);
        InventoryService.updateProduct(product.getName(), product.getCategory(), product.getPrice(), product.getQuantity());

        //Update the orders Table
        String query = "INSERT INTO orders (user_id, product_id, quantity) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, user.getUserId());
            stmt.setInt(2, product.getProductId());
            stmt.setInt(3, quantity);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Order placed successfully!");
            } else {
                System.out.println("Failed to place order.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
