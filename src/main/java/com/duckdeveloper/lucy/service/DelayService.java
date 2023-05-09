package com.duckdeveloper.lucy.service;

import com.duckdeveloper.lucy.proxy.UserProxy;
import com.duckdeveloper.lucy.type.DelayType;
import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.entities.User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DelayService {

    private final UserProxy userProxy;

    public boolean hasDelay(User user, DelayType delayType) {
        var userData = this.userProxy.get(user);
        if (userData == null || !userData.hasDelay(delayType))
            return false;

        var delay = userData.getDelay(delayType);
        if (delay.isExpired()) {
            userData.removeDelay(delayType);
            userProxy.clearUser(userData);
            return false;
        }

        return true;
    }

}
