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
 * Handles {@link Message} when {@link BotCondition} is {@link BotCondition#START_MENU}.
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
        return botCondition.equals(BotCondition.START_MENU);
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
                        + "\n\nЭто митинг-бот организации ООО \"Норд Диджитал\"."
                        + "\n\nЧтобы начать пользоваться ботом необходимо зарегистрироваться."
                        + "\nДля этого нажмите кнопку \"Зарегистрироваться\" внизу."
                        + Emoji.MENU
                        + "\n\nПосле регистрации, владелец бота подтвердит вашу регистрацию. "
                        + "\nИ можно будет начинать внесение данных.")
                .row()
                .button("Зарегистрироваться")
                .endRow()
//                .row()
//                .button("Помощь")
//                .endRow()
                .build();
    }
}
