package ru.chursinov.meetingTelegramBot.bot.handler.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.chursinov.meetingTelegramBot.bot.BotCondition;
import ru.chursinov.meetingTelegramBot.bot.keyboard.ReplyKeyboardMarkupBuilder;
import ru.chursinov.meetingTelegramBot.util.Emoji;
import ru.chursinov.meetingTelegramBot.service.ReplyMessageService;

/**
 * Handles {@link Message} when {@link BotCondition} is {@link BotCondition#MAIN_MENU}.
 *
 * Sends reply keyboard with main menu to interact with it.
 */
@Component
public class StartMessageHandler implements MessageHandler{

    private final ReplyMessageService replyMessageService;

    @Autowired
    public StartMessageHandler(ReplyMessageService replyMessageService) {
        this.replyMessageService = replyMessageService;
    }

    @Override
    public boolean canHandle(BotCondition botCondition) {
        return botCondition.equals(BotCondition.MAIN_MENU);
    }

    @Override
    public BotApiMethod<Message> handle(Message message) {
        return message.getText().equals("/start")
                ? getMainMenu(message.getChatId(), message.getChat().getFirstName())
                : replyMessageService.getTextMessage(message.getChatId(), "Такой команды я не знаю " + Emoji.EYES);
    }

    private SendMessage getMainMenu(Long chatId, String firstName) {
        return ReplyKeyboardMarkupBuilder.create(chatId)
                .setText("Привет, " + firstName + "!"
                        + "\n\nДобро пожаловать! "
                        + "\n\nЧтобы воспользоваться ботом, нажмите нужную кнопку на появившейся клавиатуре. "
                        + Emoji.MENU)
                .row()
                .button("Заполнить информацию о своей работе")
                .endRow()
                .row()
                .button("Помощь")
                .endRow()
                .build();
    }
}
