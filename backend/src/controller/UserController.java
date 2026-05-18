package com.oerms.springboot.controller;

import com.oerms.springboot.dao.UserDAO;
import com.oerms.springboot.model.User;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {

    private final UserDAO userDAO = new UserDAO();

    // =========================
    // GET ALL USERS
    // =========================
    @ResponseBody
    @GetMapping("/users")
    public List<User> getUsers() {
        return userDAO.getAllUsers();
    }

    // =========================
    // REGISTER (FOR STUDENTS ONLY)
    // =========================
    @PostMapping("/register")
    public String register(@RequestParam String name,
                           @RequestParam String email,
                           @RequestParam String password) {

        if (userDAO.emailExists(email)) {
            return "redirect:/login.html?exists=true";
        }

        String id = userDAO.generateUserId();

        User user = new User(
                id,
                name,
                email,
                password,
                "STUDENT"   // FORCE ROLE
        );

        boolean saved = userDAO.registerUser(user);

        if (saved) {
            return "redirect:/login.html?registered=true";
        } else {
            return "redirect:/login.html?registerError=true";
        }
    }

    // =========================
    // LOGIN (FIXED ROLE CHECK)
    // =========================
    @PostMapping("/login")
    public String login(@RequestParam String email,
                        @RequestParam String password,
                        @RequestParam String role) {

        User user = userDAO.loginUser(email, password);

        if (user != null) {

            String realRole = user.getRole().trim().toUpperCase();
            role = role.trim().toUpperCase();

            if (realRole.equals(role)) {

                if (realRole.equals("ADMIN")) {
                    return "redirect:/admin-users.html?userId=" + user.getId() + "&role=" + realRole;
                }

                if (realRole.equals("LECTURER")) {
                    return "redirect:/exams.html?userId=" + user.getId() + "&role=" + realRole;
                }

                if (realRole.equals("STUDENT")) {
                    return "redirect:/exam-session.html?userId=" + user.getId() + "&role=" + realRole;
                }
            }
        }

        return "redirect:/login.html?error=1";
    }

    // =========================
    // ADMIN - ADD USER (NEW)
    // =========================
    @PostMapping("/admin/add-user")
    public String addUser(@RequestParam String name,
                          @RequestParam String email,
                          @RequestParam String password,
                          @RequestParam String role) {

        if (userDAO.emailExists(email)) {
            return "redirect:/admin-users.html?exists=true";
        }

        String id = userDAO.generateUserId();

        User user = new User(
                id,
                name,
                email,
                password,
                role.toUpperCase()
        );

        userDAO.registerUser(user);

        return "redirect:/admin-users.html?added=true";
    }
    @ResponseBody
    @DeleteMapping("/admin/delete-user")
    public String deleteUser(@RequestParam String id) {

        boolean deleted = userDAO.deleteUser(id);

        if (deleted) {
            return "User deleted successfully";
        } else {
            return "User not found";
        }
    }
}