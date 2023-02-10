package ru.chursinov.meetingTelegramBot.bot;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.chursinov.meetingTelegramBot.util.Emoji;

import java.io.Serializable;

@Slf4j
@Component
@EnableScheduling
public class TelegramBot extends TelegramLongPollingBot {

    @Getter
    @Value("${bot.username}")
    String botUsername;

    @Getter
    @Value("${bot.token}")
    String botToken;

    private final UpdateReceiver updateReceiver;

    public TelegramBot(UpdateReceiver updateReceiver) {
        this.updateReceiver = updateReceiver;
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        PartialBotApiMethod<? extends Serializable> responseToUser = updateReceiver.handleUpdate(update);

//        if (responseToUser instanceof SendDocument) {
//            try {
//                execute(
//                        (SendDocument) responseToUser);
//            }
//            catch (TelegramApiException e) {
//                log.error("Error occurred while sending message to user: {}", e.getMessage());
//            }
//        }
//
//        if (responseToUser instanceof SendPhoto) {
//            try {
//                execute(
//                        (SendPhoto) responseToUser);
//            } catch (TelegramApiException e) {
//                log.error("Error occurred while sending message to user: {}", e.getMessage());
//            }
//        }

        if (responseToUser instanceof BotApiMethod) {
            try {
                execute(
                        (BotApiMethod<? extends Serializable>) responseToUser);
            } catch (TelegramApiException e) {
                log.error("Error occurred while sending message to user: {}", e.getMessage());
            }
        }

    }

    @Scheduled(cron = "0 * * * * *")
    private void sendAds(){

        try {
            execute(new SendMessage("-1001877192947", "Test message"));
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }

    }

}
