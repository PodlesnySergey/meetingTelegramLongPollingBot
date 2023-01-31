package ru.chursinov.meetingTelegramBot.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

/**
 * Resetting the bot to its initial state
 */
@Component
public class ResetCondition {

    private final BotConditionUserContext botConditionUserContext;

    @Autowired
    public ResetCondition(BotConditionUserContext botConditionUserContext) {
        this.botConditionUserContext = botConditionUserContext;
    }

    public void resetBotCondition(Message message) {
        BotCondition botCondition = BotCondition.START_MENU;
        Long userId = message.getFrom().getId();
        botConditionUserContext.setCurrentBotConditionForUserWithId(userId, botCondition);
    }
}
