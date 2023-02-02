package ru.chursinov.meetingTelegramBot.bot.handler.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.chursinov.meetingTelegramBot.bot.BotCondition;
import ru.chursinov.meetingTelegramBot.bot.BotConditionUserContext;
import ru.chursinov.meetingTelegramBot.bot.UserProfileCache;
import ru.chursinov.meetingTelegramBot.entity.UserProfileData;
import ru.chursinov.meetingTelegramBot.service.ReplyMessageService;

@Component
public class ProblemsDetailHandler implements MessageHandler{

    private final ReplyMessageService replyMessageService;
    private final BotConditionUserContext botConditionUserContext;
    private final SaveAnswers saveAnswers;
    private final UserProfileCache userProfileCache;

    @Autowired
    public ProblemsDetailHandler(ReplyMessageService replyMessageService, BotConditionUserContext botConditionUserContext, SaveAnswers saveAnswers, UserProfileCache userProfileCache) {
        this.replyMessageService = replyMessageService;
        this.botConditionUserContext = botConditionUserContext;
        this.saveAnswers = saveAnswers;
        this.userProfileCache = userProfileCache;
    }

    @Override
    public boolean canHandle(BotCondition botCondition) {
        return botCondition.equals(BotCondition.PROBLEMS_DETAILS);
    }

    @Override
    public BotApiMethod<Message> handle(Message message) {
        Long chatId = message.getChatId();

        BotCondition botCondition = BotCondition.PROFILE_FILLED;
        Long userId = message.getFrom().getId();

        UserProfileData userProfileData = userProfileCache.getUserProfileData(userId);
        userProfileData.setProblem_details(message.getText());
        userProfileCache.saveUserProfileData(userId, userProfileData);

        botConditionUserContext.setCurrentBotConditionForUserWithId(userId, botCondition);

        String username = message.getFrom().getUserName();
        saveAnswers.saveUserAnswers(userId, username);

        return replyMessageService.getTextMessage(chatId, "Ответы сохранены. Спасибо!");
    }
}
