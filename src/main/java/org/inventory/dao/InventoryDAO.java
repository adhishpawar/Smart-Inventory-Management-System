package org.inventory.dao;

import org.inventory.models.Product;
import org.inventory.utils.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InventoryDAO {


    public static List<Product> displayAllInventory() {
        String query = "SELECT p.product_id, p.name, c.name AS category, p.price, p.quantity FROM products p JOIN categories c ON p.category_id = c.category_id ";
        List<Product> products = new ArrayList<>();
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                products.add(new Product(
                        rs.getInt("product_id"),
                        rs.getString("name"),
                        rs.getString("category"),
                        rs.getDouble("price"),
                        rs.getInt("quantity")
                ));
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public boolean registerProduct(Product product) {
        String query = "INSERT INTO products (name, category_id, quantity, price, created_at) " +
                "VALUES (?, (SELECT category_id FROM categories WHERE name = ? LIMIT 1), ?, ?, NOW())";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, product.getName());
            stmt.setString(2, product.getCategory());
            stmt.setInt(3, product.getQuantity());
            stmt.setDouble(4, product.getPrice());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean updateProduct(Product product) {
        Product oldProduct = searchProductByUsingName(product.getName());
        if (oldProduct == null) {
            System.out.println("Product Was Not in Inventory.....Now Added");
            return false;
        }

        String insertCategoryQuery = "INSERT INTO categories (name) " +
                "SELECT ? FROM DUAL WHERE NOT EXISTS (SELECT 1 FROM categories WHERE name = ? LIMIT 1)";

        String updateProductQuery = "UPDATE products " +
                "SET name = ?, category_id = (SELECT category_id FROM categories WHERE name = ? LIMIT 1), " +
                "quantity = ?, price = ?, created_at = NOW() WHERE product_id = ?";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false); // Start Transaction

            // Step 1: Insert category if it does not exist
            try (PreparedStatement insertStmt = conn.prepareStatement(insertCategoryQuery)) {
                insertStmt.setString(1, product.getCategory());
                insertStmt.setString(2, product.getCategory());
                insertStmt.executeUpdate();
            }

            // Step 2: Update the product details
            try (PreparedStatement updateStmt = conn.prepareStatement(updateProductQuery)) {
                updateStmt.setString(1, product.getName());
                updateStmt.setString(2, product.getCategory());
                updateStmt.setInt(3, product.getQuantity());
                updateStmt.setDouble(4, product.getPrice());
                updateStmt.setInt(5, oldProduct.getProductId());

                int rowsAffected = updateStmt.executeUpdate();
                if (rowsAffected > 0) {
                    conn.commit(); // Commit transaction if successful
                    return true;
                } else {
                    conn.rollback(); // Rollback in case of failure
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public int getProductId(String prodName) {
        String query = "SELECT product_id FROM products WHERE name = ? ";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, prodName);
            ResultSet rs = stmt.executeQuery();

            if(rs.next())
            {
                return rs.getInt("product_id");
            }

        }catch (SQLException e)
        {
            e.printStackTrace();
        }

        return 0;
    }
    public static Product searchProductByUsingName(String name)
    {
        String query = "SELECT p.product_id, p.name, c.name AS category, p.price, p.quantity " +
                    "FROM products p " +
                    "LEFT JOIN categories c ON p.category_id = c.category_id " +
                    "WHERE p.name = ?";

        try(Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setString(1,name);
            ResultSet rs = stmt.executeQuery();
            if(rs.next()){
                return new Product(
                        rs.getInt("product_id"),
                        rs.getString("name"),
                        rs.getString("category"),
                        rs.getDouble("price"),
                        rs.getInt("quantity")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public static List<Product> searchProductByUsingCategory(String category) {
        String query = "SELECT p.product_id, p.name, c.name AS category, p.price, p.quantity FROM products p JOIN categories c ON p.category_id = c.category_id WHERE c.name = ?";
        List<Product> products = new ArrayList<>();
        try(Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, category);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                products.add(new Product(
                        rs.getInt("product_id"),
                        rs.getString("name"),
                        rs.getString("category"),
                        rs.getDouble("price"),
                        rs.getInt("quantity")
                ));
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public boolean deleteProduct(Product product) {
        // Execute DELETE query
        String deleteQuery = "DELETE FROM products WHERE product_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(deleteQuery)) {

            stmt.setInt(1, product.getProductId());
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Product deleted successfully.");
                return true;
            } else {
                System.out.println("Failed to delete product.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


}
