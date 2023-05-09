package com.duckdeveloper.lucy.proxy;

import net.dv8tion.jda.api.entities.User;
import com.duckdeveloper.lucy.model.domain.UserData;
import org.cache2k.Cache;
import org.cache2k.Cache2kBuilder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UserProxy {

    private final Cache<String, UserData> cachedUsers;

    public UserProxy() {
        this.cachedUsers = Cache2kBuilder
                .of(String.class, UserData.class)
                .name("users-proxy")
                .eternal(true)
                .build();
    }

    public UserData add(UserData user) {
        cachedUsers.put(user.getUser().getId(), user);
        return get(user.getUser());
    }

    public boolean has(User user) {
        return cachedUsers.containsKey(user.getId());
    }

    public UserData get(User user) {
        return cachedUsers.get(user.getId());
    }

    public UserData getUserDataOrCreate(User user) {
        var userData = this.get(user);
        return Objects.requireNonNullElse(userData, this.add(UserData.fromUser(user)));
    }

    public void remove(User user) {
        cachedUsers.remove(user.getId());
    }

    public void clearUser(UserData user) {
        if (user.getDelays().isEmpty() && user.getInteractions().isEmpty() && user.getWaiting().isEmpty()) {
            remove(user.getUser());
        }
    }
}