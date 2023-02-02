package ru.chursinov.meetingTelegramBot.bot.handler.callbackquery;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ru.chursinov.meetingTelegramBot.bot.BotCondition;
import ru.chursinov.meetingTelegramBot.bot.BotConditionUserContext;
import ru.chursinov.meetingTelegramBot.bot.UserProfileCache;
import ru.chursinov.meetingTelegramBot.bot.handler.message.SaveAnswers;
import ru.chursinov.meetingTelegramBot.entity.UserProfileData;
import ru.chursinov.meetingTelegramBot.service.ReplyMessageService;
import ru.chursinov.meetingTelegramBot.util.Emoji;

import java.io.Serializable;

@Slf4j
@Component
public class QueryHandler implements CallbackQueryHandler{

    private final ReplyMessageService replyMessageService;
    private final BotConditionUserContext botConditionUserContext;
    private final SaveAnswers saveAnswers;
    private final UserProfileCache userProfileCache;

    @Autowired
    public QueryHandler(ReplyMessageService replyMessageService, BotConditionUserContext botConditionUserContext, SaveAnswers saveAnswers, UserProfileCache userProfileCache) {
        this.replyMessageService = replyMessageService;
        this.botConditionUserContext = botConditionUserContext;
        this.saveAnswers = saveAnswers;
        this.userProfileCache = userProfileCache;
    }

    @Override
    public PartialBotApiMethod<? extends Serializable> handleCallbackQuery(CallbackQuery callbackQuery) {
        String callBackData = callbackQuery.getData();
        Integer messageId = callbackQuery.getMessage().getMessageId();
        Long chatId = callbackQuery.getMessage().getChatId();
        BotCondition botCondition;
        UserProfileData userProfileData;

        switch (callBackData) {
            case "YES":
                botCondition = BotCondition.PROBLEMS_DETAILS;
                botConditionUserContext.setCurrentBotConditionForUserWithId(chatId, botCondition);

                userProfileData = userProfileCache.getUserProfileData(chatId);
                userProfileData.setProblem("Да");
                userProfileCache.saveUserProfileData(chatId, userProfileData);

                return replyMessageService.getEditedTextMessage(chatId, messageId, "Опишите проблемы");
            case "NO":
                botCondition = BotCondition.PROFILE_FILLED;
                botConditionUserContext.setCurrentBotConditionForUserWithId(chatId, botCondition);

                userProfileData = userProfileCache.getUserProfileData(chatId);
                userProfileData.setProblem("Нет");
                userProfileData.setProblem_details("Нет");
                userProfileCache.saveUserProfileData(chatId, userProfileData);

                String username = callbackQuery.getFrom().getUserName();
                saveAnswers.saveUserAnswers(chatId, username);
                return replyMessageService.getEditedTextMessage(chatId, messageId,
                        String.join("\n\n",
                        "Хорошо, когда нет проблем " + Emoji.SMILE,
                        "Ответы сохранены. Хорошего дня!"));
            default:
                return replyMessageService.getTextMessage(chatId, "Не удалось обработать ответ");
        }
    }

}
