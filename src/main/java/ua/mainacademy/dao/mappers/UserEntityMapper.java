package ua.mainacademy.dao.mappers;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ua.mainacademy.dao.entity.UserEntity;
import ua.mainacademy.model.User;

@Component
@NoArgsConstructor
public class UserEntityMapper {

    public User toUser(UserEntity userEntity) {
        return User.builder()
                .id(userEntity.getId())
                .login(userEntity.getLogin())
                .password(userEntity.getPassword())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .email(userEntity.getEmail())
                .phone(userEntity.getPhone())
                .build();
    }

    public UserEntity toUserEntity(User user) {
        return UserEntity.builder()
                .id(user.getId())
                .login(user.getLogin())
                .password(user.getPassword())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .build();
    }
}
