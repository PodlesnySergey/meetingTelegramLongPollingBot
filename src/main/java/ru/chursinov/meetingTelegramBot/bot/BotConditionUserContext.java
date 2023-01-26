package ru.chursinov.meetingTelegramBot.bot;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of {@link BotConditionObserved} that saves and extracts bot conditions to/from {@link HashMap}.
 */
@Component
public class BotConditionUserContext implements BotConditionObserved{

    private final Map<Long, BotCondition> usersBotCondition = new HashMap<>();

    @Override
    public BotCondition getCurrentBotConditionForUserById(Long userId) {
        return usersBotCondition.getOrDefault(userId, BotCondition.MAIN_MENU);
    }

    @Override
    public void setCurrentBotConditionForUserWithId(Long userId, BotCondition botCondition) {
        usersBotCondition.put(userId, botCondition);
    }
}
