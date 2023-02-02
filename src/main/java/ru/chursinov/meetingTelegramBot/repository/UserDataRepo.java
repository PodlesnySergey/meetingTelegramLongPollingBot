package ru.chursinov.meetingTelegramBot.repository;

import org.springframework.data.repository.CrudRepository;
import ru.chursinov.meetingTelegramBot.entity.UserData;

public interface UserDataRepo extends CrudRepository<UserData, Long> {

    UserData findByUserId(long userId);
}
