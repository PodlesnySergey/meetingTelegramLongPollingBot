package ru.chursinov.meetingTelegramBot.bot;

import ru.chursinov.meetingTelegramBot.entity.UserProfileData;

public interface DataCache {

    UserProfileData getUserProfileData(Long userId);

    void saveUserProfileData(Long userId, UserProfileData userProfileData);
}
