package org.inventory.services;

import org.inventory.dao.InventoryDAO;
import org.inventory.models.Product;
import org.inventory.models.User;
import org.inventory.utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public static List<Map<String,Object>> getUserOrders(int userId){
        List<Map<String,Object>> orders = new ArrayList<>();
        String query = "Select o.order_id, o.order_date, p.product_id,p.name AS product_name, o.quantity "
                        + "From Orders o JOIN products p on o.product_id = p.product_id"
                        + " where o.user_id =?";

        try(Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setInt(1,userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Map<String, Object> order = new HashMap<>();
                order.put("order_id", rs.getInt("order_id"));
                order.put("order_date", rs.getTimestamp("order_date"));
                order.put("product_id", rs.getInt("product_id"));
                order.put("product_name", rs.getString("product_name"));
                order.put("quantity", rs.getInt("quantity"));
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }
}
