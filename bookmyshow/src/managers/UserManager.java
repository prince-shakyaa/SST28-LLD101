package managers;

import models.User;

import java.util.ArrayList;
import java.util.List;

public class UserManager {

    private static UserManager instance;
    private final List<User> users;

    private UserManager() {
        this.users = new ArrayList<>();
    }

    public static UserManager getInstance() {
        if (instance == null) {
            synchronized (UserManager.class) {
                if (instance == null) {
                    instance = new UserManager();
                }
            }
        }
        return instance;
    }

    public void registerUser(User user) {
        users.add(user);
        System.out.println("[UserManager] Registered: " + user.getName() + " (ID: " + user.getUserId() + ")");
    }

    public User getUserById(int userId) {
        for (User user : users) {
            if (user.getUserId() == userId) {
                return user;
            }
        }
        return null;
    }

    public User getUserByEmail(String email) {
        for (User user : users) {
            if (user.getEmailId().equalsIgnoreCase(email)) {
                return user;
            }
        }
        return null;
    }

    public List<User> getAllUsers() {
        return users;
    }
}
