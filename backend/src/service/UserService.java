package com.oerms.springboot.service;

import com.oerms.springboot.dao.UserDAO;
import com.oerms.springboot.model.User;

import java.util.List;

public class UserService {

    private final UserDAO userDAO = new UserDAO();

    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    public User login(String email, String password) {
        return userDAO.loginUser(email, password);
    }

    public boolean register(User user) {
        return userDAO.registerUser(user);
    }

    public boolean emailExists(String email) {
        return userDAO.emailExists(email);
    }

    public String generateId() {
        return userDAO.generateUserId();
    }
}