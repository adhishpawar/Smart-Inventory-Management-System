package org.inventory.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/inventory_db";
    private static final String USER = "root";
    private static final String PASSWORD = "adhish@mysql";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // TEST THE CONNECTION
    public static void main(String[] args) {
        try (Connection conn = getConnection()) {
            if (conn != null) {
                System.out.println("✅ Database Connection Successful!");
            } else {
                System.out.println("❌ Database Connection Failed!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
