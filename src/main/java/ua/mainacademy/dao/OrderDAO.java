package ua.mainacademy.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.mainacademy.dao.entity.OrderEntity;
import ua.mainacademy.dao.entity.UserEntity;
import ua.mainacademy.model.OrderStatus;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderDAO extends JpaRepository<OrderEntity, Long> {

    List<OrderEntity> findAllByUserAndStatus(UserEntity userEntity, OrderStatus status);

    Optional<OrderEntity> findFirstByUserAndStatus(UserEntity userEntity, OrderStatus status);

}
