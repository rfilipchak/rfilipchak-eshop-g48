package ua.mainacademy.dao.mappers;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ua.mainacademy.dao.entity.UserEntity;
import ua.mainacademy.model.User;

import static ua.mainacademy.prototype.UserPrototype.aNewUser;
import static ua.mainacademy.prototype.UserPrototype.aNewUserEntity;

@SpringBootTest
@ActiveProfiles("test")
class UserEntityMapperTest {

    @Autowired
    private UserEntityMapper mapper;

    @Test
    void toUserTest() {
        User expected = aNewUser();
        UserEntity entity = aNewUserEntity();

        Assertions.assertThat(mapper.toUser(entity))
                .isEqualTo(expected);
    }

    @Test
    void toUserEntityTest() {
        User user = aNewUser();
        UserEntity expected = aNewUserEntity();

        Assertions.assertThat(mapper.toUserEntity(user))
                .isEqualTo(expected);
    }
}