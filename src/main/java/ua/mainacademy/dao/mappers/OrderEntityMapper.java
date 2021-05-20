package ua.mainacademy.dao.mappers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ua.mainacademy.dao.entity.OrderEntity;
import ua.mainacademy.model.Order;

@Component
@AllArgsConstructor
public class OrderEntityMapper {

    private UserEntityMapper userEntityMapper;

    public Order toOrder(OrderEntity orderEntity) {
        return Order.builder()
                .id(orderEntity.getId())
                .user(userEntityMapper.toUser(orderEntity.getUser()))
                .creationTime(orderEntity.getCreationTime())
                .status(orderEntity.getStatus())
                .build();
    }

    public OrderEntity toOrderEntity(Order order) {
        return OrderEntity.builder()
                .id(order.getId())
                .user(userEntityMapper.toUserEntity(order.getUser()))
                .creationTime(order.getCreationTime())
                .status(order.getStatus())
                .build();
    }
}
