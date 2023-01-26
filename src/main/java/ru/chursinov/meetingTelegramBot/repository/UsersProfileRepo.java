package ru.chursinov.meetingTelegramBot.repository;

import org.springframework.data.repository.CrudRepository;
import ru.chursinov.meetingTelegramBot.entity.UserProfileData;

public interface UsersProfileRepo extends CrudRepository<UserProfileData, String> {
    UserProfileData findByUseridAndDate(long userid, String date);
}
