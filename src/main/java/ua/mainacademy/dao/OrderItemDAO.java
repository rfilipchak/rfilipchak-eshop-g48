package ua.mainacademy.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.mainacademy.dao.entity.OrderItemEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderItemDAO extends JpaRepository<OrderItemEntity, Long> {

    List<OrderItemEntity> findAllByOrderId(long orderId);

}
