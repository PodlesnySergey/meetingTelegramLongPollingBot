package ru.chursinov.meetingTelegramBot.bot.handler.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.chursinov.meetingTelegramBot.bot.UserProfileCache;
import ru.chursinov.meetingTelegramBot.entity.UserProfileData;
import ru.chursinov.meetingTelegramBot.service.UsersProfileDataService;
import ru.chursinov.meetingTelegramBot.util.GetCurrentDate;

@Component
public class SaveAnswers {

    private final UsersProfileDataService usersProfileDataService;
    private final GetCurrentDate getCurrentDate;
    private final UserProfileCache userProfileCache;

    @Autowired
    public SaveAnswers(UsersProfileDataService usersProfileDataService, GetCurrentDate getCurrentDate, UserProfileCache userProfileCache) {
        this.usersProfileDataService = usersProfileDataService;
        this.getCurrentDate = getCurrentDate;
        this.userProfileCache = userProfileCache;
    }

    public void saveUserAnswers(long userId, String username) {
        UserProfileData userProfileData = new UserProfileData();

        UserProfileData answer = usersProfileDataService.getUserAnswer(userId, getCurrentDate.getDate());

        if (answer != null) {
            userProfileData.setId(answer.getId());
        }

        userProfileData.setUserid(userId);
        userProfileData.setUsername(username);
        userProfileData.setYesterday(userProfileCache.getUserProfileData(userId).getYesterday());
        userProfileData.setToday(userProfileCache.getUserProfileData(userId).getToday());
        userProfileData.setProblem(userProfileCache.getUserProfileData(userId).getProblem());
        userProfileData.setProblem_details(userProfileCache.getUserProfileData(userId).getProblem_details());
        userProfileData.setDate(getCurrentDate.getDate());

        usersProfileDataService.saveUserProfileData(userProfileData);

        System.out.println("Ответы сохранены");
    }

}
