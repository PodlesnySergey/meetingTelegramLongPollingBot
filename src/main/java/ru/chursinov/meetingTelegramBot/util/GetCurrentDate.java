package ru.chursinov.meetingTelegramBot.util;

import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class GetCurrentDate {
    public Timestamp getDate() {
//        LocalDate localDate = LocalDate.now();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
//        return localDate.format(formatter);
        return (new Timestamp(System.currentTimeMillis()));
    }
}
