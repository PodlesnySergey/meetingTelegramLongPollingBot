package ru.chursinov.meetingTelegramBot.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Data
@Entity(name = "usersData")
public class UserData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Long userId;

    private Long chatId;

    private String firstNameTg;

    private String lastNameTg;

    private String userNameTg;
    private String realFirstName;

    private String realLastNameTg;

    private String registerDateAt;

    private Boolean isActive;

    @OneToMany (mappedBy = "user")
    private List<UserProfileData> userProfileDataList;

}
