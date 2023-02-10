package ru.chursinov.meetingTelegramBot.bot.handler.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.chursinov.meetingTelegramBot.bot.BotCondition;
import ru.chursinov.meetingTelegramBot.bot.BotConditionUserContext;
import ru.chursinov.meetingTelegramBot.bot.UserRegCache;
import ru.chursinov.meetingTelegramBot.bot.keyboard.ReplyKeyboardMarkupBuilder;
import ru.chursinov.meetingTelegramBot.entity.UserData;
import ru.chursinov.meetingTelegramBot.repository.UserDataRepo;
import ru.chursinov.meetingTelegramBot.service.ReplyMessageService;
import ru.chursinov.meetingTelegramBot.util.Emoji;

@Component
public class RegMessageHandler implements MessageHandler {

    private final ReplyMessageService replyMessageService;
    private final BotConditionUserContext botConditionUserContext;
    private final UserRegCache userRegCache;
    private final SaveRegData saveRegData;
    private final UserDataRepo userDataRepo;
    private final UserCheck userCheck;

    @Autowired
    public RegMessageHandler(ReplyMessageService replyMessageService, BotConditionUserContext botConditionUserContext, UserRegCache userRegCache, SaveRegData saveRegData, UserDataRepo userDataRepo, UserCheck userCheck) {
        this.replyMessageService = replyMessageService;
        this.botConditionUserContext = botConditionUserContext;
        this.userRegCache = userRegCache;
        this.saveRegData = saveRegData;
        this.userDataRepo = userDataRepo;
        this.userCheck = userCheck;
    }


    @Override
    public boolean canHandle(BotCondition botCondition) {
        return botCondition.equals(BotCondition.REGISTRATION);
    }

    @Override
    public BotApiMethod<Message> handle(Message message) {
        Long chatId = message.getChatId();

        long userId = message.getFrom().getId();

        if (!userCheck.isExist(userId)) {
            if (message.getText().equals("Зарегистрироваться") || message.getText().equals("/registration")) {
                return replyMessageService.getTextMessage(chatId, "Пожалуйста, введите имя и фамилию (через пробел).");
            }

            BotCondition botCondition = BotCondition.REGISTRATION_WAIT;
            UserData user = userRegCache.getUserRegCache(userId);
            user.setChatId(message.getChatId());
            user.setFirstNameTg(message.getFrom().getFirstName());
            user.setLastNameTg(message.getFrom().getLastName());
            user.setUserNameTg(message.getFrom().getUserName());
            userRegCache.setUserRegCache(userId, user);
            botConditionUserContext.setCurrentBotConditionForUserWithId(userId, botCondition);

            saveRegData.saveUserRegData(userId, message.getText());

            return replyMessageService.getTextMessage(chatId, "Ожидайте активации.");
        } else if (userCheck.isExist(userId) & !userCheck.isActive(userId)) {
            return replyMessageService.getTextMessage(chatId, "Вы уже отправляли данные для регистрации. Ожидайте активации.");
        } else {
            return getFillDataMenu(message.getChatId(), message.getChat().getFirstName());
        }
    }

    private SendMessage getFillDataMenu(Long chatId, String firstName) {
        return ReplyKeyboardMarkupBuilder.create(chatId)
                .setText("Вы уже зарегистрированы и прошли подтверждение."
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
