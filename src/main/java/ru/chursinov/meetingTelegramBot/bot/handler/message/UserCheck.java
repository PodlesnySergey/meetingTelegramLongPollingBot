package ru.chursinov.meetingTelegramBot.bot.handler.message;

import org.springframework.stereotype.Component;
import ru.chursinov.meetingTelegramBot.entity.UserData;
import ru.chursinov.meetingTelegramBot.repository.UserDataRepo;
@Component
public class UserCheck {
    private final UserDataRepo userDataRepo;

    public UserCheck(UserDataRepo userDataRepo) {
        this.userDataRepo = userDataRepo;
    }

    public boolean isExist(long userId) {
        UserData userData = userDataRepo.findByUserId(userId);
        if (userData != null) {
            return true;
        } else return false;
    }

    public boolean isActive(long userId) {
        UserData userData = userDataRepo.findByUserId(userId);
        return userData.getIsActive();
    }

}
