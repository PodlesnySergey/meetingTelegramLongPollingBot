package ru.chursinov.meetingTelegramBot.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Данные анкеты пользователя
 */

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@Entity
@Table(name = "user_answers")
public class UserProfileData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    Timestamp date;

    String username;

    long userid;

    @Column(length = 2048)
    String yesterday;
    @Column(length = 2048)
    String today;
    String problem;
    @Column(length = 2048)
    String problem_details;
}

