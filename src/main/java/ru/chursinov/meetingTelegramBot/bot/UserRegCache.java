package ru.chursinov.meetingTelegramBot.bot;

import org.springframework.stereotype.Component;
import ru.chursinov.meetingTelegramBot.entity.UserData;
import java.util.HashMap;
import java.util.Map;
@Component
public class UserRegCache {
    Map<Long, UserData> userRegCache = new HashMap<>();

    public UserData getUserRegCache(long userId) {
        UserData user = userRegCache.get(userId);
        if (user == null) {
            user = new UserData();
        }
        return user;
    }

    public void setUserRegCache(long userId, UserData user) {
        userRegCache.put(userId, user);
    }

}
