package ru.chursinov.meetingTelegramBot.bot;

import org.springframework.stereotype.Component;
import ru.chursinov.meetingTelegramBot.entity.UserProfileData;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserDataCache implements DataCache{
    private final Map<Long, UserProfileData> usersProfileData = new HashMap<>();

    @Override
    public UserProfileData getUserProfileData(Long userId) {
        UserProfileData userProfileData = usersProfileData.get(userId);
        if (userProfileData == null) {
            userProfileData = new UserProfileData();
        }
        return userProfileData;
    }

    @Override
    public void saveUserProfileData(Long userId, UserProfileData userProfileData) {
        usersProfileData.put(userId, userProfileData);
    }
}
