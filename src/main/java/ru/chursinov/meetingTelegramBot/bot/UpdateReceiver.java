package ru.chursinov.meetingTelegramBot.bot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.chursinov.meetingTelegramBot.bot.handler.BotConditionHandler;
import ru.chursinov.meetingTelegramBot.bot.handler.callbackquery.QueryHandler;
import ru.chursinov.meetingTelegramBot.service.ReplyMessageService;

import java.io.Serializable;

@Slf4j
@Component
public class UpdateReceiver {

    private final BotConditionHandler botConditionHandler;
    private final BotConditionUserContext botConditionUserContext;
    private final QueryHandler callbackQueryHandler;
    private final ReplyMessageService replyMessageService;

    @Autowired
    public UpdateReceiver(BotConditionHandler botConditionHandler, BotConditionUserContext botConditionUserContext, QueryHandler callbackQueryHandler, ReplyMessageService replyMessageService) {
        this.botConditionHandler = botConditionHandler;
        this.botConditionUserContext = botConditionUserContext;
        this.callbackQueryHandler = callbackQueryHandler;
        this.replyMessageService = replyMessageService;
    }

    /**
     * Distributes incoming {@link Update} by its type and returns prepared response to user from specific handlers to main executable method.
     */
    public PartialBotApiMethod<? extends Serializable> handleUpdate(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            Message message = update.getMessage();
            BotCondition botCondition = getBotCondition(message);
            log.info(
                    "Message from: {}; " +
                            "chat id: {};  " +
                            "text: {}; " +
                            "bot condition: {}",
                    message.getFrom().getUserName(),
                    message.getChatId(),
                    message.getText(),
                    botCondition
            );

            return botConditionHandler.handleTextMessageByCondition(message, botCondition);
        }
        else if (update.hasCallbackQuery()) {
            CallbackQuery callbackQuery = update.getCallbackQuery();
            log.info(
                    "CallbackQuery from: {}; " +
                            "data: {}; " +
                            "message id: {}",
                    callbackQuery.getFrom().getUserName(),
                    callbackQuery.getData(),
                    callbackQuery.getId()
            );

            return callbackQueryHandler.handleCallbackQuery(callbackQuery);
        }
        else {
            log.error(
                    "Unsupported request from: {}; " +
                            "chatId: {}",
                    update.getMessage().getFrom().getUserName(),
                    update.getMessage().getChatId()
            );

            return replyMessageService.getTextMessage(update.getMessage().getChatId(), "Я могу принимать только текстовые сообщения!");
        }
    }

    /**
     * Defines current bot condition by user message to handle updates further in specific handlers.
     */
    private BotCondition getBotCondition(Message message) {
        Long userId = message.getFrom().getId();
        String userTextMessage = message.getText();
        BotCondition botCondition;

        switch (userTextMessage) {
            case "/start":
                botCondition = BotCondition.MAIN_MENU;
                break;
            case "Заполнить информацию о своей работе":
                botCondition = BotCondition.START_QUESTIONS;
                break;
            case "Помощь":
            case "/help":
                botCondition = BotCondition.HELP;
                break;
            case "/answers":
                botCondition = BotCondition.SHOW_PROFILE;
                break;
            default:
                botCondition = botConditionUserContext.getCurrentBotConditionForUserById(userId);
        }
        botConditionUserContext.setCurrentBotConditionForUserWithId(userId, botCondition);
        return botCondition;
    }

}
