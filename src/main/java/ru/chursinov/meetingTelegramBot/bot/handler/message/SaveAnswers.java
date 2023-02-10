package ru.chursinov.meetingTelegramBot.bot.handler.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.chursinov.meetingTelegramBot.bot.UserProfileCache;
import ru.chursinov.meetingTelegramBot.entity.UserData;
import ru.chursinov.meetingTelegramBot.entity.UserProfileData;
import ru.chursinov.meetingTelegramBot.service.UserDataService;
import ru.chursinov.meetingTelegramBot.service.UsersProfileDataService;
import ru.chursinov.meetingTelegramBot.util.GetCurrentDate;

@Component
public class SaveAnswers {

    private final UsersProfileDataService usersProfileDataService;
    private final UserDataService userDataService;
    private final GetCurrentDate getCurrentDate;
    private final UserProfileCache userProfileCache;

    @Autowired
    public SaveAnswers(UsersProfileDataService usersProfileDataService, UserDataService userDataService, GetCurrentDate getCurrentDate, UserProfileCache userProfileCache) {
        this.usersProfileDataService = usersProfileDataService;
        this.userDataService = userDataService;
        this.getCurrentDate = getCurrentDate;
        this.userProfileCache = userProfileCache;
    }

    public void saveUserAnswers(long userId, String username) {
        UserData user = userDataService.getUser(userId);
        UserProfileData userProfileData = new UserProfileData();
        UserProfileData answer = usersProfileDataService.getUserAnswer(user, getCurrentDate.getDate());

        if (answer != null) {
            userProfileData.setId(answer.getId());
        }

        userProfileData.setYesterday(userProfileCache.getUserProfileData(userId).getYesterday());
        userProfileData.setToday(userProfileCache.getUserProfileData(userId).getToday());
        userProfileData.setProblem(userProfileCache.getUserProfileData(userId).getProblem());
        userProfileData.setProblem_details(userProfileCache.getUserProfileData(userId).getProblem_details());
        userProfileData.setFillDate(getCurrentDate.getDate());
        userProfileData.setUser(user);

        usersProfileDataService.saveUserProfileData(userProfileData);

        System.out.println("Ответы сохранены");
    }

}
