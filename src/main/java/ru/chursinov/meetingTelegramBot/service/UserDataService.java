package ru.chursinov.meetingTelegramBot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.chursinov.meetingTelegramBot.entity.UserData;
import ru.chursinov.meetingTelegramBot.repository.UserDataRepo;

@Service
public class UserDataService {

    private final UserDataRepo usersRepository;

    @Autowired
    public UserDataService(UserDataRepo usersRepository) {
        this.usersRepository = usersRepository;
    }

    public void saveUsers(UserData user) {
        usersRepository.save(user);
    }

    public UserData getUser(long id) {
        return usersRepository.findByUserId(id);
    }
}
