package org.inventory.services;

import org.inventory.dao.UserDAO;
import org.inventory.models.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class AuthService {
    private final UserDAO userDAO = new UserDAO();

    //Hash Password Using SHA-256
    private String hashPasword(String password){
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[]  hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for(byte b : hash){
                hexString.append(String.format("%02x",b));
            }
            return hexString.toString();
        }catch (NoSuchAlgorithmException e)
        {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    public boolean register(String name,String email, String password, String role){
        String hashPassword = hashPasword(password);
        User user = new User(0,name,email, hashPassword,role);
        return userDAO.registerUser(user);
    }

    //Login User
    public User login(String email, String password){
        User user = userDAO.getUserByEmail(email);
        if(user != null && user.getPasswordHash().equals(hashPasword(password))){
            return user;
        }
        return null;
    }
}
