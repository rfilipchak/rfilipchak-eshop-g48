package ua.mainacademy.dao.mappers;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import ua.mainacademy.dao.entity.OrderItemEntity;
import ua.mainacademy.model.OrderItem;

@Component
@AllArgsConstructor
public class OrderItemEntityMapper {

    private OrderEntityMapper orderMapper;
    private ItemEntityMapper itemMapper;

    public OrderItem toOrderItem(OrderItemEntity orderItemEntity) {

        return OrderItem.builder()
                .id(orderItemEntity.getId())
                .order(orderMapper.toOrder(orderItemEntity.getOrder()))
                .item(itemMapper.toItem(orderItemEntity.getItem()))
                .amount(orderItemEntity.getAmount())
                .build();
    }

    public OrderItemEntity toOrderItemEntity(OrderItem orderItem) {

        return OrderItemEntity.builder()
                .id(orderItem.getId())
                .order(orderMapper.toOrderEntity(orderItem.getOrder()))
                .item(itemMapper.toItemEntity(orderItem.getItem()))
                .amount(orderItem.getAmount())
                .build();
    }
}
