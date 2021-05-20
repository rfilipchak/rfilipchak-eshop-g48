package ua.mainacademy.dao;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ua.mainacademy.dao.entity.UserEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static ua.mainacademy.prototype.UserPrototype.aNewUserEntityWithoutId;

@SpringBootTest
@ActiveProfiles("test")
class UserDAOTest {

    @Autowired
    private UserDAO userDAO;

    private UserEntity savedUser;

    @BeforeEach
    void setUp() {
        savedUser = userDAO.save(aNewUserEntityWithoutId());
    }

    @AfterEach
    void tearDown() {
        userDAO.delete(savedUser);
    }

    @Test
    void shouldCreateUser() {
        assertThat(savedUser.getId()).isNotNull();
    }

    @Test
    void findAllByLoginAndPassword() {
        List<UserEntity> users = userDAO.findAllByLoginAndPassword("roman123", "1234");

        assertNotNull(users);
        assertFalse(users.isEmpty());
        assertEquals(users.get(0).getId(), savedUser.getId());
    }

    @Test
    void findAllByEmail() {
        List<UserEntity> users = userDAO.findAllByEmail("roman@gmail.com");

        assertNotNull(users);
        assertFalse(users.isEmpty());
        assertEquals(users.get(0).getId(), savedUser.getId());
    }

    @Test
    void findAllByLogin() {
        List<UserEntity> users = userDAO.findAllByLogin("roman123");

        assertNotNull(users);
        assertFalse(users.isEmpty());
        assertEquals(users.get(0).getId(), savedUser.getId());
    }

    @Test
    void findAll(){
        UserEntity secondSavedUser = userDAO.save(aNewUserEntityWithoutId()
                .toBuilder().login("new")
                .password("new")
                .build());
        List<UserEntity> users = userDAO.findAll();

        assertNotNull(users);
        Assertions.assertThat(users.size()).isEqualTo(2);
        assertEquals(users.get(0).getId(), savedUser.getId());
        assertEquals(users.get(1).getId(), secondSavedUser.getId());
        userDAO.delete(secondSavedUser);
    }

    @Test
    void findAllByLoginIsEmpty(){
        List<UserEntity> users = userDAO.findAll();
        assertFalse(users.isEmpty());

        assertThat(userDAO.findAllByLogin("roman1234")).isEmpty();
    }
}