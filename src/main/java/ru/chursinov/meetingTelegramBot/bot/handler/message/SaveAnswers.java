package ru.chursinov.meetingTelegramBot.bot.handler.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.chursinov.meetingTelegramBot.bot.UserDataCache;
import ru.chursinov.meetingTelegramBot.entity.UserProfileData;
import ru.chursinov.meetingTelegramBot.service.UsersProfileDataService;
import ru.chursinov.meetingTelegramBot.util.GetCurrentDate;

@Component
public class SaveAnswers {

    private final UsersProfileDataService usersProfileDataService;
    private final GetCurrentDate getCurrentDate;
    private final UserDataCache userDataCache;

    @Autowired
    public SaveAnswers(UsersProfileDataService usersProfileDataService, GetCurrentDate getCurrentDate, UserDataCache userDataCache) {
        this.usersProfileDataService = usersProfileDataService;
        this.getCurrentDate = getCurrentDate;
        this.userDataCache = userDataCache;
    }

    public void saveUserAnswers(long userId, String username) {
        UserProfileData userProfileData = new UserProfileData();

        UserProfileData answer = usersProfileDataService.getUserAnswer(userId, getCurrentDate.getDate());

        if (answer != null) {
            userProfileData.setId(answer.getId());
        }

        userProfileData.setUserid(userId);
        userProfileData.setUsername(username);
        userProfileData.setYesterday(userDataCache.getUserProfileData(userId).getYesterday());
        userProfileData.setToday(userDataCache.getUserProfileData(userId).getToday());
        userProfileData.setProblem(userDataCache.getUserProfileData(userId).getProblem());
        userProfileData.setProblem_details(userDataCache.getUserProfileData(userId).getProblem_details());
        userProfileData.setDate(getCurrentDate.getDate());

        usersProfileDataService.saveUserProfileData(userProfileData);

        System.out.println("Ответы сохранены");
    }

}
