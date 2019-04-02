package net.rodrigocarvalho.lucy.dao;

import net.dv8tion.jda.api.entities.User;
import net.rodrigocarvalho.lucy.factory.UserData;

import java.util.HashMap;
import java.util.Map;

public class UserDao {

    private static final Map<String, UserData> USERS = new HashMap<>();

    public static void add(UserData user) {
        USERS.put(user.getUser().getId(), user);
    }

    public static boolean has(User user) {
        return USERS.containsKey(user.getId());
    }

    public static UserData get(User user) {
        return USERS.get(user.getId());
    }

    public static void remove(User user) {
        USERS.remove(user.getId());
    }

    public static void clearUser(UserData user) {
        if (user.getDelays().isEmpty() && user.getReactions().isEmpty()) {
            remove(user.getUser());
        }
    }
}