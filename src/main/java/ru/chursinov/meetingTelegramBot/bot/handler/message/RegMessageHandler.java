package ru.chursinov.meetingTelegramBot.bot.handler.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.chursinov.meetingTelegramBot.bot.BotCondition;
import ru.chursinov.meetingTelegramBot.bot.BotConditionUserContext;
import ru.chursinov.meetingTelegramBot.bot.UserRegCache;
import ru.chursinov.meetingTelegramBot.entity.UserData;
import ru.chursinov.meetingTelegramBot.service.ReplyMessageService;

@Component
public class RegMessageHandler implements MessageHandler {

    private final ReplyMessageService replyMessageService;
    private final BotConditionUserContext botConditionUserContext;
    private final UserRegCache userRegCache;
    private final SaveRegData saveRegData;

    @Autowired
    public RegMessageHandler(ReplyMessageService replyMessageService, BotConditionUserContext botConditionUserContext, UserRegCache userRegCache, SaveRegData saveRegData) {
        this.replyMessageService = replyMessageService;
        this.botConditionUserContext = botConditionUserContext;
        this.userRegCache = userRegCache;
        this.saveRegData = saveRegData;
    }


    @Override
    public boolean canHandle(BotCondition botCondition) {
        return botCondition.equals(BotCondition.REGISTRATION);
    }

    @Override
    public BotApiMethod<Message> handle(Message message) {
        Long chatId = message.getChatId();

        if (message.getText().equals("Зарегистрироваться")) {
            return replyMessageService.getTextMessage(chatId, "Введите фамилию и имя (через пробел).");
        }

        BotCondition botCondition = BotCondition.REGISTRATION_WAIT;
        Long userId = message.getFrom().getId();

        UserData user = userRegCache.getUserRegCache(userId);
        user.setChatId(message.getChatId());
        user.setFirstNameTg(message.getFrom().getFirstName());
        user.setLastNameTg(message.getFrom().getLastName());
        user.setUserNameTg(message.getFrom().getUserName());

        userRegCache.setUserRegCache(userId, user);


//        userProfileData.setYesterday(message.getText());
//        userDataCache.saveUserProfileData(userId, userProfileData);


        botConditionUserContext.setCurrentBotConditionForUserWithId(userId, botCondition);

        saveRegData.saveUserRegData(userId, message.getText());

        return replyMessageService.getTextMessage(chatId, "Ожидайте активации.");
    }
}
