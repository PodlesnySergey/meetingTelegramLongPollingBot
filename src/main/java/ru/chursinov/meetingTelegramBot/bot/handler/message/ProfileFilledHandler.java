package ru.chursinov.meetingTelegramBot.bot.handler.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.chursinov.meetingTelegramBot.bot.BotCondition;
import ru.chursinov.meetingTelegramBot.service.ReplyMessageService;

@Component
public class ProfileFilledHandler implements MessageHandler{

    private final ReplyMessageService replyMessageService;
    private final SaveAnswers saveAnswers;

    @Autowired
    public ProfileFilledHandler(ReplyMessageService replyMessageService, SaveAnswers saveAnswers) {
        this.replyMessageService = replyMessageService;
        this.saveAnswers = saveAnswers;
    }

    @Override
    public boolean canHandle(BotCondition botCondition) {
        return botCondition.equals(BotCondition.PROFILE_FILLED);
    }

    @Override
    public SendMessage handle(Message message) {
        Long chatId = message.getChatId();

        return replyMessageService.getTextMessage(chatId, "Ответы сохранены. Спасибо!");
    }
}
