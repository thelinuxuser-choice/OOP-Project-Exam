package com.oerms.springboot.dao;

import com.oerms.springboot.model.User;

import java.io.*;
import java.util.*;

public class UserDAO {

    // Always use backend/users.txt
    private static String getFilePath() {
        return "backend/users.txt";
    }

    // =========================
    // REGISTER USER
    // =========================
    public boolean registerUser(User user) {

        try (BufferedWriter writer =
                     new BufferedWriter(new FileWriter(getFilePath(), true))) {

            writer.write(
                    user.getId().trim() + "," +
                            user.getName().trim() + "," +
                            user.getEmail().trim() + "," +
                            user.getPassword().trim() + "," +
                            user.getRole().trim()
            );

            writer.newLine();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // =========================
    // CHECK EMAIL EXISTS
    // =========================
    public boolean emailExists(String email) {

        List<User> users = getAllUsers();

        for (User user : users) {
            if (user.getEmail() != null &&
                    user.getEmail().trim().equalsIgnoreCase(email.trim())) {
                return true;
            }
        }

        return false;
    }

    // =========================
    // LOGIN USER
    // =========================
    public User loginUser(String email, String password) {

        List<User> users = getAllUsers();

        for (User u : users) {

            if (u.getEmail() != null &&
                    u.getPassword() != null &&
                    u.getEmail().trim().equalsIgnoreCase(email.trim()) &&
                    u.getPassword().trim().equals(password.trim())) {

                return u;
            }
        }

        return null;
    }

    // =========================
    // GET ALL USERS
    // =========================
    public List<User> getAllUsers() {

        List<User> users = new ArrayList<>();

        File file = new File(getFilePath());

        if (!file.exists()) {
            return users;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

            String line;

            while ((line = reader.readLine()) != null) {

                String[] data = line.split(",");

                if (data.length == 5) {

                    users.add(new User(
                            data[0].trim(),
                            data[1].trim(),
                            data[2].trim(),
                            data[3].trim(),
                            data[4].trim()
                    ));
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return users;
    }

    // =========================
    // GENERATE USER ID (SAFE)
    // =========================
    public String generateUserId() {

        List<User> users = getAllUsers();

        int max = 0;

        for (User u : users) {
            try {
                if (u.getId() != null && u.getId().startsWith("U")) {
                    String num = u.getId().substring(1).trim();
                    max = Math.max(max, Integer.parseInt(num));
                }
            } catch (Exception ignored) {}
        }

        return "U" + String.format("%03d", max + 1);
    }
    public boolean deleteUser(String userId) {

        List<User> users = getAllUsers();
        List<User> updatedUsers = new ArrayList<>();

        boolean removed = false;

        for (User u : users) {
            if (!u.getId().equalsIgnoreCase(userId)) {
                updatedUsers.add(u);
            } else {
                removed = true;
            }
        }

        if (!removed) return false;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(getFilePath(), false))) {

            for (User u : updatedUsers) {
                writer.write(
                        u.getId() + "," +
                                u.getName() + "," +
                                u.getEmail() + "," +
                                u.getPassword() + "," +
                                u.getRole()
                );
                writer.newLine();
            }

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}