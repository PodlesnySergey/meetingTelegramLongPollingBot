package ru.chursinov.meetingTelegramBot.repository;

import org.springframework.data.repository.CrudRepository;
import ru.chursinov.meetingTelegramBot.entity.UserData;
import ru.chursinov.meetingTelegramBot.entity.UserProfileData;

import java.sql.Timestamp;

public interface UsersProfileRepo extends CrudRepository<UserProfileData, String> {
    UserProfileData findByUserAndFillDate(UserData user, String fillDate);
}
