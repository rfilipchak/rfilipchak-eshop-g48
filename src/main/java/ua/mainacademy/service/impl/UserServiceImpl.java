package ua.mainacademy.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ua.mainacademy.dao.UserDAO;
import ua.mainacademy.dao.entity.UserEntity;
import ua.mainacademy.dao.mappers.UserEntityMapper;
import ua.mainacademy.model.User;
import ua.mainacademy.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;
    private final UserEntityMapper mapper;

    @Override
    public User create(User user) {
        if (nonNull(user) && isNull(user.getId()) && userDAO.findAllByLogin(user.getLogin()).isEmpty()) {
            return mapper.toUser(userDAO.save(mapper.toUserEntity(user)));
        }
        throw new RuntimeException(String.format("User %s can not be created.", user));
    }

    @Override
    public User update(User user) {
        //We can update only existing user with unique login
        if (nonNull(user) && nonNull(user.getId())) {
            UserEntity savedUser = userDAO.findById(user.getId())
                    .orElseThrow(() -> new RuntimeException("User was not found"));
            if (!user.getLogin().equals(savedUser.getLogin())
                    && !userDAO.findAllByLogin(user.getLogin()).isEmpty()) {
                throw new RuntimeException("User can not be updated");
            }
            return mapper.toUser(userDAO.save(mapper.toUserEntity(user)));
        }
        throw new RuntimeException("User can not be updated");
    }

    @Override
    public User findOneById(long id) {
            return mapper.toUser(userDAO.findById(id)
                    .orElseThrow(() -> new RuntimeException("User was not found")));
    }

    @Override
    public List<User> findAllByLoginAndPassword(String login, String password) {
        return userDAO.findAllByLoginAndPassword(login, password).stream()
                .map(mapper::toUser)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> findAll() {
        return userDAO.findAll().stream()
                .map(mapper::toUser)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(long id) {
        userDAO.deleteById(id);
    }
}
