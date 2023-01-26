package ru.chursinov.meetingTelegramBot.bot;

/**
 * Helps to find out what condition the bot is in for each user.
 * Also allows to set current condition for the bot.
 */
public interface BotConditionObserved {

    BotCondition getCurrentBotConditionForUserById(Long userId);

    void setCurrentBotConditionForUserWithId(Long userId, BotCondition botCondition);

}
