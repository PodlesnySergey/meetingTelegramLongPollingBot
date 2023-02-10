package ru.chursinov.meetingTelegramBot.bot.handler.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.chursinov.meetingTelegramBot.bot.UserRegCache;
import ru.chursinov.meetingTelegramBot.entity.UserData;
import ru.chursinov.meetingTelegramBot.service.UserDataService;
import ru.chursinov.meetingTelegramBot.util.GetCurrentDate;

@Component
public class SaveRegData {
    private final UserDataService userDataService;
    private final GetCurrentDate getCurrentDate;
    private final UserRegCache userRegCache;
@Autowired
    public SaveRegData(UserDataService userDataService, GetCurrentDate getCurrentDate, UserRegCache userRegCache) {
        this.userDataService = userDataService;
        this.getCurrentDate = getCurrentDate;
        this.userRegCache = userRegCache;
    }

    public void saveUserRegData(long userId, String firstLastName) {

        UserData userData = new UserData();
        String[] words = firstLastName.split("\\s");

        userData.setId(userRegCache.getUserRegCache(userId).getId());
        userData.setUserId(userId);
        userData.setChatId(userRegCache.getUserRegCache(userId).getChatId());
        userData.setFirstNameTg(userRegCache.getUserRegCache(userId).getFirstNameTg());
        userData.setLastNameTg(userRegCache.getUserRegCache(userId).getLastNameTg());
        userData.setUserNameTg(userRegCache.getUserRegCache(userId).getUserNameTg());
        userData.setRealFirstName(words[0]);
        userData.setRealLastNameTg(words[1]);
        userData.setRegisterDateAt(getCurrentDate.getDate());
        userData.setIsActive(false);

        userDataService.saveUsers(userData);

        System.out.println("Данные пользователя сохранены.");
    }

}
