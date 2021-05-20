package ua.mainacademy.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import ua.mainacademy.dao.UserDAO;
import ua.mainacademy.dao.mappers.UserEntityMapper;
import ua.mainacademy.model.User;

import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static ua.mainacademy.prototype.UserPrototype.aNewUser;
import static ua.mainacademy.prototype.UserPrototype.aNewUserEntity;

@SpringBootTest
@ActiveProfiles("test")
class UserServiceTest {

    @Autowired
    private UserService service;
    @MockBean
    private UserDAO userDAO;
    @Autowired
    private UserEntityMapper mapper;

    @Test
    void createTest() {
        User userForCreating = aNewUser().toBuilder().id(null).build();

        when(userDAO.findAllByLogin(userForCreating.getLogin())).thenReturn(emptyList());
        when(userDAO.save(mapper.toUserEntity(userForCreating))).thenReturn(aNewUserEntity());

        assertThat(service.create(userForCreating)).isEqualTo(aNewUser());
    }

    @Test
    void creatingErrorForExistingId() {
        User userForCreating = aNewUser();

        assertThrows(RuntimeException.class, () -> {
            service.create(userForCreating);
        });
    }

    @Test
    void creatingErrorForNull() {
        assertThrows(RuntimeException.class, () -> {
            service.create(null);
        });
    }

    @Test
    void creatingErrorForExistingLogin() {
        User userForCreating = aNewUser().toBuilder().id(null).build();

        when(userDAO.findAllByLogin(userForCreating.getLogin())).thenReturn(List.of(aNewUserEntity()));

        assertThrows(RuntimeException.class, () -> {
            service.create(userForCreating);
        });
    }

    @Test
    void updateTest() {
        User userForUpdate = aNewUser();

        when(userDAO.findById(userForUpdate.getId())).thenReturn(Optional.of(aNewUserEntity()));
        when(userDAO.findAllByLogin(userForUpdate.getLogin())).thenReturn(emptyList());
        when(userDAO.save(mapper.toUserEntity(userForUpdate))).thenReturn(aNewUserEntity());

        assertThat(service.update(userForUpdate)).isEqualTo(aNewUser());
    }

    @Test
    void updateWitNewLoginTest() {
        User userForUpdate = aNewUser().toBuilder().login("new").build();

        when(userDAO.findById(userForUpdate.getId())).thenReturn(Optional.of(aNewUserEntity()));
        when(userDAO.findAllByLogin(userForUpdate.getLogin())).thenReturn(emptyList());
        when(userDAO.save(mapper.toUserEntity(userForUpdate))).thenReturn(aNewUserEntity());

        assertThat(service.update(userForUpdate)).isEqualTo(aNewUser());
    }

    @Test
    void updatingErrorForNewUser() {
        User userForUpdate = aNewUser().toBuilder().id(null).build();

        assertThrows(RuntimeException.class, () -> {
            service.update(userForUpdate);
        });
    }

    @Test
    void updatingErrorIfUserDoesNotExist() {
        User userForUpdate = aNewUser();

        when(userDAO.findById(userForUpdate.getId())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            service.update(userForUpdate);
        });
    }

    @Test
    void updatingErrorIfLoginDoesNotUnique() {
        User userForUpdate = aNewUser();

        when(userDAO.findById(userForUpdate.getId())).thenReturn(Optional.of(aNewUserEntity()
                .toBuilder()
                .login("new")
                .build()));
        when(userDAO.findAllByLogin(userForUpdate.getLogin())).thenReturn(List.of(aNewUserEntity()));

        assertThrows(RuntimeException.class, () -> {
            service.update(userForUpdate);
        });
    }

    @Test
    void updatingErrorForNull() {
        assertThrows(RuntimeException.class, () -> {
            service.update(null);
        });
    }

    @Test
    void findOneByIdTest() {
        when(userDAO.findById(1L)).thenReturn(Optional.of(aNewUserEntity()));

        assertThat(service.findOneById(1L)).isEqualTo(aNewUser());
    }

    @Test
    void errorForFindOneByIdTest() {
        when(userDAO.findById(2L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            service.findOneById(2L);
        });
    }

    @Test
    void findAllByLoginAndPasswordTest() {
        String login = "roman123";
        String password = "1234";

        when(userDAO.findAllByLoginAndPassword(login, password)).thenReturn(List.of(aNewUserEntity()));

        assertThat(service.findAllByLoginAndPassword(login, password)).isEqualTo(List.of(aNewUser()));
    }

    @Test
    void findNothingByLoginAndPasswordTest() {
        String login = "roman123";
        String password = "1234";

        when(userDAO.findAllByLoginAndPassword(login, password)).thenReturn(emptyList());

        assertThat(service.findAllByLoginAndPassword(login, password)).isEqualTo(emptyList());
    }

    @Test
    void findAllTest() {
        when(userDAO.findAll()).thenReturn(List.of(aNewUserEntity(), aNewUserEntity()
                .toBuilder().id(2L)
                .login("new")
                .build()));

        assertThat(service.findAll()).isEqualTo(List.of(aNewUser(), aNewUser()
                .toBuilder().id(2L)
                .login("new")
                .build()));
    }
    @Test
    void findAllEmptyTest() {
        when(userDAO.findAll()).thenReturn(emptyList());

        assertThat(service.findAll()).isEqualTo(emptyList());
    }

    @Test
    void deleteByIdTest() {
        service.deleteById(1L);
        verify(userDAO, times(1)).deleteById(1L);
    }
}