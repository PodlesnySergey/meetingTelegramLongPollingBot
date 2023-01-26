package ru.chursinov.meetingTelegramBot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.chursinov.meetingTelegramBot.entity.UserProfileData;
import ru.chursinov.meetingTelegramBot.repository.UsersProfileRepo;

@Service
public class UsersProfileDataService {

    private final UsersProfileRepo usersProfileRepo;

    @Autowired
    public UsersProfileDataService(UsersProfileRepo usersProfileRepo) {
        this.usersProfileRepo = usersProfileRepo;
    }

    public void saveUserProfileData(UserProfileData userProfileData) {
        usersProfileRepo.save(userProfileData);
    }

    public UserProfileData getUserAnswer(long id, String date) {
        return usersProfileRepo.findByUseridAndDate(id, date);
    }
}
