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
    private final UserCheck userCheck;

    @Autowired
    public StartMessageHandler(ReplyMessageService replyMessageService, UserCheck userCheck) {
        this.replyMessageService = replyMessageService;
        this.userCheck = userCheck;
    }

    @Override
    public boolean canHandle(BotCondition botCondition) {
        return botCondition.equals(BotCondition.START_MENU);
    }

    @Override
    public BotApiMethod<Message> handle(Message message) {
//        return message.getText().equals("/start")
//                ? getMainMenu(message.getChatId(), message.getChat().getFirstName())
//                : replyMessageService.getTextMessage(message.getChatId(), "Такой команды я не знаю " + Emoji.EYES);

        long userId = message.getFrom().getId();

        if (message.getText().equals("/start")) {
               if (!userCheck.isExist(userId)) {
                    return getMainMenuNotReg(message.getChatId(), message.getChat().getFirstName());
                } else if (userCheck.isActive(userId)) {
                    return getMainMenuReg(message.getChatId(), message.getChat().getFirstName());
                } else {
                    return replyMessageService.getTextMessage(message.getChatId(), "Ваша регистрация еще не подтверждена.\n"
                    + "Ожидайте подтверждения от владельца бота.");
                }
        } else {
            return replyMessageService.getTextMessage(message.getChatId(), "Такой команды я не знаю " + Emoji.EYES);
        }

    }

    private SendMessage getMainMenuNotReg(Long chatId, String firstName) {
        return ReplyKeyboardMarkupBuilder.create(chatId)
                .setText("Привет, " + firstName + "!"
                        + "\n\nДобро пожаловать! "
                        + "\n\nЭто митинг-бот организации ООО \"Норд Диджитал\"."
                        + "\n\nЧтобы начать пользоваться ботом необходимо зарегистрироваться."
                        + "\nДля этого нажмите кнопку \"Зарегистрироваться\" внизу или введите команду /registration."
                        + Emoji.MENU
                        + "\n\nПосле регистрации, владелец бота подтвердит вашу регистрацию. "
                        + "\nИ можно будет начинать внесение данных.")
                .row()
                .button("Зарегистрироваться")
                .endRow()
                .build();
    }

    private SendMessage getMainMenuReg(Long chatId, String firstName) {
        return ReplyKeyboardMarkupBuilder.create(chatId)
                .setText("Привет, " + firstName + "!"
                        + "\n\nВы уже зарегистрированы и прошли подтверждение."
                        + "\nМожете начинать внесение данных."
                        + "\nДля этого нажмите кнопку \"Заполнить информацию о своей работе\" внизу "
                        + Emoji.MENU
                        + "\nили введите команду /filldata.")
                .row()
                .button("Заполнить информацию о своей работе")
                .endRow()
                .row()
                .button("Помощь")
                .endRow()
                .build();
    }
}
