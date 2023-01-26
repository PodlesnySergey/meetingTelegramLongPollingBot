package ru.chursinov.meetingTelegramBot.bot.handler.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.chursinov.meetingTelegramBot.bot.BotCondition;
import ru.chursinov.meetingTelegramBot.bot.BotConditionUserContext;
import ru.chursinov.meetingTelegramBot.bot.UserDataCache;
import ru.chursinov.meetingTelegramBot.bot.keyboard.InlineKeyboardMarkupBuilder;
import ru.chursinov.meetingTelegramBot.entity.UserProfileData;
import ru.chursinov.meetingTelegramBot.util.Emoji;

@Slf4j
@Component
public class TodayQuestionHandler implements MessageHandler{

    private final BotConditionUserContext botConditionUserContext;
    private final UserDataCache userDataCache;

    @Autowired
    public TodayQuestionHandler(BotConditionUserContext botConditionUserContext, UserDataCache userDataCache) {
        this.botConditionUserContext = botConditionUserContext;
        this.userDataCache = userDataCache;
    }

    @Override
    public boolean canHandle(BotCondition botCondition) {
        return botCondition.equals(BotCondition.TODAY);
    }

    @Override
    public SendMessage handle(Message message) {

        Long chatId = message.getChatId();

        BotCondition botCondition = BotCondition.PROBLEMS;
        Long userId = message.getFrom().getId();

        UserProfileData userProfileData = userDataCache.getUserProfileData(userId);
        userProfileData.setToday(message.getText());
        userDataCache.saveUserProfileData(userId, userProfileData);

        botConditionUserContext.setCurrentBotConditionForUserWithId(userId, botCondition);

        return isProblem(chatId);
    }

    private SendMessage isProblem(Long chatId) {
        return InlineKeyboardMarkupBuilder.create(chatId)
                .setText("Есть проблемы блокирующие работу?")
                .row()
                .button("Да " + Emoji.CONFUSED, "YES")
                .button("Нет " + Emoji.SMILE, "NO")
                .endRow()
                .build();
    }

}
