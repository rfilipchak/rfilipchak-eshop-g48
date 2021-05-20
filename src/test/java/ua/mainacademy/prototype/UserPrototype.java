package ua.mainacademy.prototype;

import ua.mainacademy.dao.entity.UserEntity;
import ua.mainacademy.model.User;

public class UserPrototype {

    public static User aNewUser(){
        return User.builder()
                .id(1L)
                .phone("+321781")
                .email("roman@gmail.com")
                .lastName("Fill")
                .firstName("Roman")
                .password("1234")
                .login("roman123")
                .build();
    }

    public static UserEntity aNewUserEntity(){
        return UserEntity.builder()
                .id(1L)
                .phone("+321781")
                .email("roman@gmail.com")
                .lastName("Fill")
                .firstName("Roman")
                .password("1234")
                .login("roman123")
                .build();
    }

    public static UserEntity aNewUserEntityWithoutId(){
        return UserEntity.builder()
                .phone("+321781")
                .email("roman@gmail.com")
                .lastName("Fill")
                .firstName("Roman")
                .password("1234")
                .login("roman123")
                .build();
    }
}
