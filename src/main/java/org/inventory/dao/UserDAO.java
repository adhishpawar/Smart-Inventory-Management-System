package org.inventory.dao;

import org.inventory.models.User;
import org.inventory.utils.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    //register New User
    public boolean registerUser(User user)
    {
        String query = "INSERT INTO users (name, email, password_hash, role) VALUES (?, ?, ?, ?)";
        try(Connection conn = DBConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setString(1,user.getName());
            stmt.setString(2,user.getEmail());
            stmt.setString(3,user.getPasswordHash());
            stmt.setString(4,user.getRole());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    //Fetch User by Email --- > Login
    public User getUserByEmail(String email){
        String query = "SELECT * FROM users WHERE email = ?";
        try(Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setString(1,email);
            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                return new User(
                        rs.getInt("user_id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password_hash"),
                        rs.getString("role")
                );
            }
        }catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
