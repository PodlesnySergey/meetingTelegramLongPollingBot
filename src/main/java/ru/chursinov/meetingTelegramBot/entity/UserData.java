package ru.chursinov.meetingTelegramBot.entity;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

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

    private String registeredAt;

    private Boolean isActive;

//    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId") //CascadeType.ALL - если удаляем пользователя, то удаляются и все задачи связанные с пользователем
//    private List<UserProfileData> profileData;

}
