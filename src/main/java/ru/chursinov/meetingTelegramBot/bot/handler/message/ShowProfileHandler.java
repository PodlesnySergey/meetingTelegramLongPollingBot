package ru.chursinov.meetingTelegramBot.bot.handler.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.chursinov.meetingTelegramBot.bot.BotCondition;
import ru.chursinov.meetingTelegramBot.bot.ResetCondition;
import ru.chursinov.meetingTelegramBot.entity.UserProfileData;
import ru.chursinov.meetingTelegramBot.service.UsersProfileDataService;
import ru.chursinov.meetingTelegramBot.util.Emoji;
import ru.chursinov.meetingTelegramBot.util.GetCurrentDate;

@Component
public class ShowProfileHandler implements MessageHandler {

    private final UsersProfileDataService usersProfileDataService;
    private final GetCurrentDate getCurrentDate;
    private final ResetCondition resetCondition;

    @Autowired
    public ShowProfileHandler(UsersProfileDataService usersProfileDataService, GetCurrentDate getCurrentDate, ResetCondition resetCondition) {
        this.usersProfileDataService = usersProfileDataService;
        this.getCurrentDate = getCurrentDate;
        this.resetCondition = resetCondition;
    }

    @Override
    public boolean canHandle(BotCondition botCondition) {
        return botCondition.equals(BotCondition.SHOW_PROFILE);
    }

    @Override
    public BotApiMethod<Message> handle(Message message) {

        Long userId = message.getFrom().getId();

        UserProfileData answer = usersProfileDataService.getUserAnswer(userId, getCurrentDate.getDate());
        resetCondition.resetBotCondition(message);

        if (answer != null) {
            return new SendMessage(Long.toString(message.getChatId()), String.format("%s%n --------------------------------------" +
                            "%nСделано вчера: %n%s%n " +
                            "%nПланы на сегодня: %n%s%n " +
                            "%nЕсть ли проблемы: %n%s%n " +
                            "%nОписание проблем: %n%s%n",
                    "Ваши ответы " + Emoji.CALENDAR + " " + answer.getDate(),
                    answer.getYesterday(),
                    answer.getToday(),
                    answer.getProblem(),
                    answer.getProblem_details()));
        } else {
            return new SendMessage(Long.toString(message.getChatId()), Emoji.POINT_UP + " Вы сегодня ещё не отвечали на вопросы бота");
        }

    }
}
