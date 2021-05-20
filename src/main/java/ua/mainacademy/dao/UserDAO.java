package ua.mainacademy.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.mainacademy.dao.entity.UserEntity;

import java.util.List;

@Repository
public interface UserDAO extends JpaRepository<UserEntity, Long> {

    List<UserEntity> findAllByLoginAndPassword(String login, String password);

    List<UserEntity> findAllByEmail(String userEmail);

    List<UserEntity> findAllByLogin(String login);

}
