package ru.chursinov.meetingTelegramBot.bot.handler.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.chursinov.meetingTelegramBot.bot.BotCondition;
import ru.chursinov.meetingTelegramBot.bot.BotConditionUserContext;
import ru.chursinov.meetingTelegramBot.bot.ResetCondition;
import ru.chursinov.meetingTelegramBot.service.ReplyMessageService;

/**
 * Handles {@link Message} when {@link BotCondition} is {@link BotCondition#HELP}.
 *
 * Informs how the bot works.
 */
@Component
public class HelpMessageHandler implements MessageHandler{

    private final ReplyMessageService replyMessageService;
    private final ResetCondition resetCondition;

    @Autowired
    public HelpMessageHandler(ReplyMessageService replyMessageService, ResetCondition resetCondition) {
        this.replyMessageService = replyMessageService;
        this.resetCondition = resetCondition;
    }

    @Override
    public boolean canHandle(BotCondition botCondition) {
        return botCondition.equals(BotCondition.HELP);
    }

    @Override
    public SendMessage handle(Message message) {
        Long chatId = message.getChatId();
        resetCondition.resetBotCondition(message);
        return replyMessageService.getTextMessage(chatId,
                String.join("\n\n",
                        "Бот предназначен для опроса сотрудников о работе.",
                        "Чтобы начать внесение данных введите команду /filldata",
                        "Бот задаст вопросы, на которые нужно ответить:" +
                        "\n1) Что было сделано вчера?" +
                        "\n2) Какие рабочие планы на сегодня?" +
                        "\n3) Есть проблемы блокирующие работу?",
                        "Бот сохранит ответы.",
                        "Для того, чтобы посмотреть ответы введите команду /answers" +
                                " или выберите соответствующий пункт в меню."
                ));
    }
}
